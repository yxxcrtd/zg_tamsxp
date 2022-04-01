/*
 * 文件名：ChbPrdDeclareSubSetService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年9月24日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */

package com.joyintech.component.svrreport.chb.msgassemble.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.background.component.common.pack.common.util.DateUtil;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.ChbConstant;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.component.svrreport.chb.dto.ChbBondSubmitPara;
import com.joyintech.component.svrreport.chb.dto.ChbMsgBuildInfoDto;
import com.joyintech.component.svrreport.chb.dto.ChbPrdDeclareSubDto;
import com.joyintech.component.svrreport.chb.dto.CnBondInterfItemDto;
import com.joyintech.component.svrreport.chb.util.ChbXmlUtil;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;


/**
 * 设置 产品申报登记
 * 
 * @description
 * @author 赵永生
 * @version 1.0 2020年9月24日
 * @see ChbPrdDeclareSubSetService
 * @since 1.0
 */
@Service
public class ChbPrdDeclareSubSetService {

	private static Logger logger = LoggerFactory.getLogger(ChbPrdDeclareSubSetService.class);

	@Autowired
	private ChbMsgSetCommonService chbMsgSetCommonService;

	/**
	 * 设置消息
	 * 
	 * @param datas
	 * @return
	 * @see
	 * @since 1.0
	 */
	public ChbMsgBuildInfoDto setMsg(List<ChbPrdDeclareSubDto> datas) throws Exception {
		logger.debug("开始组装产品申报登记报送报文");
		//报文拼接：2、进行数据结构转化，把[1]中取得的List，转化为报文拼接组件中调用方法的数据结构类
		ChbBondSubmitPara chbBondSubmitPara = generateBondMessage(datas);
		List<String> attachFiles = new ArrayList<>();
		
		for(ChbPrdDeclareSubDto data : datas){
			if (StrUtil.isNotEmpty(data.getAttachFilePath())) {
				try {
					attachFiles.add(reBuildFile(data.getAttachFilePath(),data.getProdId()));
				} catch (IOException e) {
					logger.error("申报文件组装失败,请检查文件: ",e);
					throw new Exception();
				}
			}
		}
		
		
		chbBondSubmitPara.setAttachPathList(attachFiles );
		//拼接报文
		ChbMsgBuildInfoDto chbMsgBuildInfoDto = chbMsgSetCommonService.buildMsg(chbBondSubmitPara);
		chbMsgBuildInfoDto.setChbBondSubmitPara(chbBondSubmitPara);
		logger.debug("结束组装产品申报登记报送报文");
		
		//清理临时生成文件
		for(String file : attachFiles){
			File files = new File(file);
			files.delete();
		}
		return chbMsgBuildInfoDto;
	}
	
	
	
	/**
	 * 重新生成报送文件
	 * @param path 原上传文件目录
	 * @param prod_id 产品编号,用于重新生成文件名
	 * @return
	 * @throws IOException
	 */
	public String reBuildFile(String path,String prod_id) throws Exception{
		//新文件名称
		String newFilePath = null;
		//获取原文件名
		File zipFile = new File(path);
		//检查原文件是否存在
		if(zipFile.exists()){
			File unzipFile = null;
			File newFile = new File(zipFile.getParent()+File.separator+zipFile.getName().replace(".zip", ""));
			if(newFile.exists()){
            	//如果文件夹存在则删除文件
            	deleteAllFile(newFile);
    		}
            try {
                // 使用UTF-8 解压
               unzipFile =  ZipUtil.unzip(path, CharsetUtil.UTF_8);
            } catch (Exception e) {
                // 使用GBK 解压
                unzipFile =  ZipUtil.unzip(path, CharsetUtil.CHARSET_GBK);
            }
            //获取zip包中所有的文件
            fileAllFile(unzipFile,newFile.getPath(),prod_id);
            newFilePath= newFile.getPath();
//            //如果获取到对应文件
//            if(fileList.size()>0){
//            	//新建一个zip文件用来存放最新文件
//                File newFile = new File(newFilePath);
//                if(newFile.exists()){
//                	//如果文件夹存在则删除文件
//                	deleteAllFile(newFile);
//        		}
//                newFile.mkdir();//创建文件夹
//                for()
//            }
//            //删除原文件解压出来的文件夹
//            unzipFile.delete();
		}
		return newFilePath ;
	}
	
	
	/****
	 * 遍历获取是所有非文件夹文件
	 * @param files
	 * @return
	 */
	public void fileAllFile(File files,String directory,String prod_id){
		for(File file :files.listFiles()){
        	if(file.isDirectory()){
        		fileAllFile(file,directory,prod_id);
        	}else{
        		String newFileName = file.getName();
        		if(!newFileName.startsWith(prod_id+"_")){
        			newFileName = prod_id+"_" + newFileName;
                }
        		FileUtil.copy(file.getPath(), directory+File.separator+newFileName, true);
        	}
        	file.delete();
        }
	}
	
	/****
	 * 删除所有文件
	 * @param files
	 * @return
	 */
	public void deleteAllFile(File files){
		for(File file :files.listFiles()){
        	if(file.isDirectory()){
        		deleteAllFile(file);
        	}
        	file.delete();
        }
	}
	
	/***
	 * 重做申报报送附件附件
	 * @param zipFile
	 * @param infile
	 * @param newFileName
	 * @throws Exception 
	 */
	 public static void zipPutFile(File zipFile,List<File> infile,String prod_id) throws Exception{
	    	FileOutputStream stream = null;
	        ZipOutputStream zipOut = null;
	        BufferedInputStream bis = null;
	        //循环读取文件路径集合，获取每一个文件的路径  
	        try {
	            stream = new FileOutputStream(zipFile);
	            zipOut =new ZipOutputStream(stream);
	            for(File inputFile: infile){  
	                if(inputFile.exists()) { //判断文件是否存在  
	                    if (inputFile.isFile()) {  //判断是否属于文件，还是文件夹  
	                        //创建输入流读取文件  
	                        bis = new BufferedInputStream(new FileInputStream(inputFile));
	                        //将文件写入zip内，即将文件进行打包  
	                        String fileName = inputFile.getName();
	                        //如果文件不是以产品编码+_开头的.则将文件改名
	                        if(!fileName.startsWith(prod_id+"_")){
	                        	fileName = prod_id+"_" + fileName;
	                        }
	                        zipOut.putNextEntry(new ZipEntry(fileName));  

	                        //写入文件的方法，同上                  
	                        int size = 0;  
	                        byte[] buffer = new byte[1024];  //设置读取数据缓存大小
	                        while ((size = bis.read(buffer)) > 0) {  
	                            zipOut.write(buffer, 0, size);  
	                        }  
	                        //关闭输入输出流  
	                        zipOut.closeEntry();  
	                        bis.close();

	                    }
	                }  
	            }  
	            zipOut.flush();
	        }
	        catch (IOException e) {
	            logger.error("组装新的报送文件错误:", e);
	            throw new Exception(e.getMessage());
	        }
	        finally {
	            if (bis != null) {
	                try {
	                    bis.close();
	                }
	                catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (zipOut != null) {
	                try {
	                    zipOut.close();
	                }
	                catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (stream != null) {
	                try {
	                    stream.close();
	                }
	                catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	/**
	 * 生成标准接口的数据结构
	 * 
	 * @param datas 业务数据
	 * @return 标准接口的数据结构
	 */
	private ChbBondSubmitPara generateBondMessage(List<ChbPrdDeclareSubDto> datas) {
		if(StringUtil.isEmptyList(datas)){
			return new ChbBondSubmitPara();
		}
		ChbBondSubmitPara res = new ChbBondSubmitPara();
		
		List<Map<String, CnBondInterfItemDto>> infos = new ArrayList<>();
		Map<String,String> registSeqKeyMap = new HashMap<String,String>();
		
		List<String> registSeq = new ArrayList<String>();
		//Map<String, String> registSeqKeyMap = new LinkedHashMap<String, String>();
		datas.forEach(item -> {
			//利率统一除以100
			ChbXmlUtil.rateFormat(item);
			
			Map<String, CnBondInterfItemDto> cnBondItemMap = new LinkedHashMap<String, CnBondInterfItemDto>();
			 String currRegistSeq = "";
			// modify by zhangliang 20210130 重新发送不需要重生生成流水号 start
			if (StringUtil.isNotEmpty(item.getSumitSeq())){
				registSeq.add(item.getSumitSeq());
				//add by wudebao 20210424 在para里面保存唯一标识符 start
				//设置唯一标识
				registSeqKeyMap.put(item.getSumitSeq(), item.getProdInsId());				
				//add by wudebao 20210424 在para里面保存唯一标识符 end
			}
			else{
				currRegistSeq = Sid.nextShort(20);
				item.setSumitSeq(currRegistSeq);
				registSeq.add(currRegistSeq);
				//add by wudebao 20210424 在para里面保存唯一标识符 start
				//设置唯一标识
				registSeqKeyMap.put(currRegistSeq, item.getProdInsId());				
				//add by wudebao 20210424 在para里面保存唯一标识符 end
			}
//            if(DictCodeList.报送反馈状态_发送失败.getValue().equals(item.getSubmitFeedbackStatus())
//                    ||DictCodeList.报送反馈状态_中债退回.getValue().equals(item.getSubmitFeedbackStatus())){
//                currRegistSeq = Sid.nextShort(20);
//                registSeq.add(currRegistSeq);
//            }
//            else if(StringUtil.isEmpty(item.getSumitSeq())){
//                currRegistSeq = Sid.nextShort(20);
//                registSeq.add(currRegistSeq);
//            }
//            else{
//                registSeq.add(item.getSumitSeq());
//            }
			generateNodes(cnBondItemMap, item);
			infos.add(cnBondItemMap);
			
			
		});
		
		res.setDealType(DictCodeList.报表名称_产品申报登记.getValue());
		res.setSysDate(DateUtil.now());
	    //res.setEquityConfDate(equityConfDate);
		res.setRegistBankCode(datas.get(0).getIssuePartyId());
	//	List<String> registSeq = new ArrayList<String>();

		res.setRegistSeq(registSeq);
		//res.setRegistSeqKeyMap(registSeqKeyMap);
		res.setInfos(infos);

		//add by wudebao 20210424 在para里面保存唯一标识符 start
		//设置唯一标识
		res.setRegistSeqKeyMap(registSeqKeyMap);
		
		//add by wudebao 20210424 在para里面保存唯一标识符 end
		
		return res;
	}
	
	/**
	 * 生成标准接口的数据结构
	 * 
	 *
	 * @return 标准接口的数据结构
	 */
	private void generateNodes(Map<String, CnBondInterfItemDto> cnBondItemMap, ChbPrdDeclareSubDto sub) {

			//产品名称
			if(StringUtil.isNotEmpty(sub.getProdName())){
			CnBondInterfItemDto cnBondItem1 = new CnBondInterfItemDto();
		    cnBondItem1.setValue(sub.getProdName());
		    cnBondItemMap.put("ProductName", cnBondItem1);
			}
		    //行内标识码
			if(StringUtil.isNotEmpty(sub.getProdInsId())){
		    CnBondInterfItemDto cnBondItem2 = new CnBondInterfItemDto();
		    cnBondItem2.setValue(sub.getProdInsId());
		    cnBondItemMap.put("PreidentificationCode", cnBondItem2);
			}
		    //发行机构代码
		    if(StringUtil.isNotEmpty(sub.getIssuePartyId())){
		    CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
		    cnBondItem3.setValue(sub.getIssuePartyId());
		    cnBondItemMap.put("IssuerIdentificationCode", cnBondItem3);
		    }





		    //产品审批人身份证号
		    if(StringUtil.isNotEmpty(sub.getApproverId())){
		    CnBondInterfItemDto cnBondItem4 = new CnBondInterfItemDto();
		    cnBondItem4.setValue(sub.getApproverId());
		    cnBondItemMap.put("ProductApproverIdNumber", cnBondItem4);
		    }
		    //产品设计人身份证号
		    if(StringUtil.isNotEmpty(sub.getDesignerId())){
		    CnBondInterfItemDto cnBondItem5 = new CnBondInterfItemDto();
		    cnBondItem5.setValue(sub.getDesignerId());
		    cnBondItemMap.put("ProductDesignerIdNumber", cnBondItem5);
		    }
		    //投资经理身份证号
		    if(StringUtil.isNotEmpty(sub.getManagerId())){
		    CnBondInterfItemDto cnBondItem6 = new CnBondInterfItemDto();
		    cnBondItem6.setValue(sub.getManagerId());
		    cnBondItemMap.put("InvestmentManagerIdNumber", cnBondItem6);
		    }
		    //业务联络人姓名
		    if(StringUtil.isNotEmpty(sub.getLiaisonName())){
		    CnBondInterfItemDto cnBondItem7 = new CnBondInterfItemDto();
		    cnBondItem7.setValue(sub.getLiaisonName());
		    cnBondItemMap.put("FilingContactName", cnBondItem7);
		    }
		    //业务联络人座机
		    if(StringUtil.isNotEmpty(sub.getLiaisonTel())){
		    CnBondInterfItemDto cnBondItem8 = new CnBondInterfItemDto();
		    cnBondItem8.setValue(sub.getLiaisonTel());
		    cnBondItemMap.put("FilingContactWorkPhone", cnBondItem8);
		    }
		    //业务联络人手机
		    if(StringUtil.isNotEmpty(sub.getLiaisonMobile())){
		    CnBondInterfItemDto cnBondItem9 = new CnBondInterfItemDto();
		    cnBondItem9.setValue(sub.getLiaisonMobile());
		    cnBondItemMap.put("FilingContactMobilePhone", cnBondItem9);
		    }
		    //业务联络人邮箱
		    if(StringUtil.isNotEmpty(sub.getLiaisonEmail())){
		    CnBondInterfItemDto cnBondItem10 = new CnBondInterfItemDto();
		    cnBondItem10.setValue(sub.getLiaisonEmail());
		    cnBondItemMap.put("FilingContactEmail", cnBondItem10);
		    }
		    //产品收益类型
		    if(StringUtil.isNotEmpty(sub.getProfitType())){
		    CnBondInterfItemDto cnBondItem11 = new CnBondInterfItemDto();
		    cnBondItem11.setValue(sub.getProfitType());
		    cnBondItemMap.put("ProductReturnType", cnBondItem11);
		    }

			//新老产品标记
			if(StringUtil.isNotEmpty(sub.getIsFhxgcp())){
				CnBondInterfItemDto cnBondItem12 = new CnBondInterfItemDto();
				cnBondItem12.setValue(sub.getIsFhxgcp());
				cnBondItemMap.put("NewProductPleaseSelect", cnBondItem12);
			}
			//产品期限
			if(StringUtil.isNotEmpty(sub.getTermtype())){
				CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
				cnBondItem13.setValue(sub.getTermtype());
				cnBondItemMap.put("ProductTermToMaturityOfPublicOrPrivateProduct", cnBondItem13);
			}
		    //是否金融同业专属
		    if(StringUtil.isNotEmpty(sub.getExcFinSector())){
		    CnBondInterfItemDto cnBondItem13 = new CnBondInterfItemDto();
		    cnBondItem13.setValue(sub.getExcFinSector());
		    cnBondItemMap.put("FinancialInstitutionExclusivePleaseSelect", cnBondItem13);
		    }
		    //资金投向地区
		    if(StringUtil.isNotEmpty(sub.getInvestArae())){
		    CnBondInterfItemDto cnBondItem14 = new CnBondInterfItemDto();
		    cnBondItem14.setValue(sub.getInvestArae());
		    cnBondItemMap.put("InvestmentRegion", cnBondItem14);
		    }
		   		    
		    //产品投资国家或地区,该节点有子节点
		    if(StringUtil.isNotEmpty(sub.getInvestCountry())){
		    CnBondInterfItemDto cnBondItem15 = new CnBondInterfItemDto();
		    List<Map<String, CnBondInterfItemDto>> mapList1 = new ArrayList<Map<String, CnBondInterfItemDto>>();
		    String investcountry = sub.getInvestCountry();
		    String [] ics = investcountry.split(ChbConstant.COMMA);
		    for(String str : ics){
			    Map<String, CnBondInterfItemDto> cnBondItemMapSon =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
			    CnBondInterfItemDto cnBondItem15_1 = new CnBondInterfItemDto();//第一个子节点
			    cnBondItem15_1.setValue(str);
			    cnBondItemMapSon.put("SpecificCountryOrRegion",cnBondItem15_1);
			    mapList1.add(cnBondItemMapSon);
		    }
		    cnBondItem15.setMaps(mapList1);//第一个父子节点组合结束
		    cnBondItemMap.put("SpecificCountryOrRegionTuple", cnBondItem15);
		    }
		    
		    //理财业务服务模式
		    if(StringUtil.isNotEmpty(sub.getFinanSerModel())){
		    CnBondInterfItemDto cnBondItem16 = new CnBondInterfItemDto();
		    cnBondItem16.setValue(sub.getFinanSerModel());
		    cnBondItemMap.put("WealthManagementServiceMode", cnBondItem16);
		    }
		    //产品运作模式
		    if(StringUtil.isNotEmpty(sub.getOperateMode())){
		    CnBondInterfItemDto cnBondItem17 = new CnBondInterfItemDto();
		    cnBondItem17.setValue(sub.getOperateMode());
		    cnBondItemMap.put("ProductOperationMode", cnBondItem17);
		    }
		    //募集方式 01公募 02私募
		    if(StringUtil.isNotEmpty(sub.getRaiseType())){
		    CnBondInterfItemDto cnBondItem18 = new CnBondInterfItemDto();
		    cnBondItem18.setValue(sub.getRaiseType());
		    cnBondItemMap.put("TypeOfSubscription", cnBondItem18);
		    }
		    //产品资产配置方式
		    if(StringUtil.isNotEmpty(sub.getCmmode())){
		    CnBondInterfItemDto cnBondItem19 = new CnBondInterfItemDto();
		    cnBondItem19.setValue(sub.getCmmode());
		    cnBondItemMap.put("ProductAssetAllocationMethod", cnBondItem19);
		    }
		    //产品管理模式
		    if(StringUtil.isNotEmpty(sub.getPmmode())){
		    CnBondInterfItemDto cnBondItem20 = new CnBondInterfItemDto();
		    cnBondItem20.setValue(sub.getPmmode());
		    cnBondItemMap.put("ProductManagementMode", cnBondItem20);
		    }
		    //实际管理人员名称
		    if(StringUtil.isNotEmpty(sub.getMname())){
		    CnBondInterfItemDto cnBondItem21 = new CnBondInterfItemDto();
		    cnBondItem21.setValue(sub.getMname());
		    cnBondItemMap.put("ActualProductManagerName", cnBondItem21);
		    }
		    //产品定价方式
		    if(StringUtil.isNotEmpty(sub.getPricedMode())){
		    CnBondInterfItemDto cnBondItem22 = new CnBondInterfItemDto();
		    cnBondItem22.setValue(sub.getPricedMode());
		    cnBondItemMap.put("PricingMethod", cnBondItem22);
		    }
		    //投资性质 01固定收益类/02权益类/03混合类/04商品及金融衍生品类
		    if(StringUtil.isNotEmpty(sub.getIvstorientation())){
		    CnBondInterfItemDto cnBondItem23 = new CnBondInterfItemDto();
		    cnBondItem23.setValue(sub.getIvstorientation());
		    cnBondItemMap.put("TypeOfInvestmentAsset", cnBondItem23);
		    }
		    //业绩比较基准%
		    if(sub.getYield()!=null){
		    CnBondInterfItemDto cnBondItem24 = new CnBondInterfItemDto();
		    cnBondItem24.setValue(String.format("%.7f",sub.getYield()));
		    cnBondItem24.setUnitRef(ChbConstant.PURE);
		    cnBondItem24.setDecimals("7");
		    cnBondItemMap.put("PerformanceBenchmarkRate", cnBondItem24);
		    }
		    //是否设置最短持有期限
		    if(StringUtil.isNotEmpty(sub.getIsShortTerm())){
		    CnBondInterfItemDto cnBondItem25 = new CnBondInterfItemDto();
		    cnBondItem25.setValue(sub.getIsShortTerm());
		    cnBondItemMap.put("MinimumHoldingPeriodPleaseSelect", cnBondItem25);
		    }
		    //最短持有期限(天)
		    if(sub.getShortTerm()!=null){
		    CnBondInterfItemDto cnBondItem26 = new CnBondInterfItemDto();
		    cnBondItem26.setValue(String.valueOf(sub.getShortTerm().setScale(0, BigDecimal.ROUND_HALF_UP)));
		    cnBondItem26.setUnitRef(ChbConstant.PURE);
		    cnBondItem26.setDecimals("0");
		    cnBondItemMap.put("MinimumHoldingPeriodDays", cnBondItem26);
		    }
		    //最短持有期后是否自由赎回
		    if(StringUtil.isNotEmpty(sub.getIsFreeRedem())){
		    CnBondInterfItemDto cnBondItem27 = new CnBondInterfItemDto();
		    cnBondItem27.setValue(sub.getIsFreeRedem());
		    cnBondItemMap.put("OptionOfRedemptionAfterMinimumHoldingPeriodPleaseSelect", cnBondItem27);
		    }
		    //是否现金管理类
		    if(StringUtil.isNotEmpty(sub.getCashManaCate())){
		    CnBondInterfItemDto cnBondItem28 = new CnBondInterfItemDto();
		    cnBondItem28.setValue(sub.getCashManaCate());
		    cnBondItemMap.put("CashManagementPleaseSelect", cnBondItem28);
		    }
		    //产品销售区域父子节点
		    if(StringUtil.isNotEmpty(sub.getSaleArea())){
		    CnBondInterfItemDto cnBondItem29 = new CnBondInterfItemDto();
		    List<Map<String, CnBondInterfItemDto>> mapList2 = new ArrayList<Map<String, CnBondInterfItemDto>>();
		    //子节点开始
		    String [] saleAreas = sub.getSaleArea().split(ChbConstant.COMMA);
		    for(String str : saleAreas){
			    Map<String, CnBondInterfItemDto> cnBondItemMap29_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map

			    CnBondInterfItemDto cnBondItem29_1 = new CnBondInterfItemDto();//第一个子节点
			    cnBondItem29_1.setValue(str);
			    cnBondItemMap29_1.put("ProductSalesRegion",cnBondItem29_1);
			    mapList2.add(cnBondItemMap29_1);		    
		    }

		    cnBondItem29.setMaps(mapList2);//第一个父子节点组合结束

		    cnBondItemMap.put("ProductSalesRegionTuple", cnBondItem29);
		    }
		    //起点销售金额
		    if(sub.getSaleAmtStr() != null){
		    CnBondInterfItemDto cnBondItem30 = new CnBondInterfItemDto();
		    cnBondItem30.setValue(String.valueOf(sub.getSaleAmtStr().setScale(2, BigDecimal.ROUND_HALF_UP)));
		    cnBondItem30.setUnitRef(ChbConstant.币种全称_中债_人民币);
		    cnBondItem30.setDecimals("2");
		    cnBondItemMap.put("InvestmentThreshold", cnBondItem30);
		    }
		    //募集币种
		    if(StringUtil.isNotEmpty(sub.getRaiseCcy())){
		    CnBondInterfItemDto cnBondItem31 = new CnBondInterfItemDto();
		    List<Map<String, CnBondInterfItemDto>> mapList3 = new ArrayList<Map<String, CnBondInterfItemDto>>();
		    //子节点开始
		    String [] raiseCcys = sub.getRaiseCcy().split(ChbConstant.COMMA);
		    for(String str : raiseCcys){
		    	Map<String, CnBondInterfItemDto> cnBondItemMap31_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
			    CnBondInterfItemDto cnBondItem31_1 = new CnBondInterfItemDto();//第一个子节点
			    cnBondItem31_1.setValue(str);
			    cnBondItemMap31_1.put("FundingCurrency",cnBondItem31_1);
			    mapList3.add(cnBondItemMap31_1);
		    }
		    cnBondItem31.setMaps(mapList3);//第一个父子节点组合结束
		    cnBondItemMap.put("FundingCurrencyTuple", cnBondItem31);
		    }
		    //兑付本金币种
		    if(StringUtil.isNotEmpty(sub.getCashPrinCcy())){
		    CnBondInterfItemDto cnBondItem32 = new CnBondInterfItemDto();
		    List<Map<String, CnBondInterfItemDto>> mapList4 = new ArrayList<Map<String, CnBondInterfItemDto>>();
		    //子节点开始
		    String [] cashPrinCcys = sub.getCashPrinCcy().split(ChbConstant.COMMA);
		    for(String str : cashPrinCcys){
			    Map<String, CnBondInterfItemDto> cnBondItemMap32_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
			    CnBondInterfItemDto cnBondItem32_1 = new CnBondInterfItemDto();//第一个子节点
			    cnBondItem32_1.setValue(str);
			    cnBondItemMap32_1.put("PrincipalPaymentCurrency",cnBondItem32_1);
			    mapList4.add(cnBondItemMap32_1);
		    }
		    cnBondItem32.setMaps(mapList4);//第一个父子节点组合结束
		    cnBondItemMap.put("PrincipalPaymentCurrencyTuple", cnBondItem32);
		    }

		    //兑付收益币种
		    if(StringUtil.isNotEmpty(sub.getCashProfCcy())){
		    CnBondInterfItemDto cnBondItem33 = new CnBondInterfItemDto();
		    List<Map<String, CnBondInterfItemDto>> mapList33 = new ArrayList<Map<String, CnBondInterfItemDto>>();
		    //子节点开始
		    String [] cashProfCcys = sub.getCashProfCcy().split(ChbConstant.COMMA);
		    for(String str : cashProfCcys){
			    Map<String, CnBondInterfItemDto> cnBondItemMap33_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
			    CnBondInterfItemDto cnBondItem33_1 = new CnBondInterfItemDto();//第一个子节点
			    cnBondItem33_1.setValue(str);
			    cnBondItemMap33_1.put("IncomePaymentCurrency",cnBondItem33_1);
			    mapList33.add(cnBondItemMap33_1);
		    }
		    cnBondItem33.setMaps(mapList33);//第一个父子节点组合结束
		    cnBondItemMap.put("IncomePaymentCurrencyTuple", cnBondItem33);
		    }
		    //销售手续费
		    if(sub.getSaleFeeRate() != null){
		    CnBondInterfItemDto cnBondItem34 = new CnBondInterfItemDto();
		    cnBondItem34.setValue(String.format("%.7f",sub.getSaleFeeRate()));
		    cnBondItem34.setUnitRef(ChbConstant.PURE);
		    cnBondItem34.setDecimals("7");
		    cnBondItemMap.put("SalesCommissionRate", cnBondItem34);
		    }
		    //托管费率
		    if(sub.getTruFeeRate() != null){
		    CnBondInterfItemDto cnBondItem35 = new CnBondInterfItemDto();
		    cnBondItem35.setValue(String.format("%.7f",sub.getTruFeeRate()));
		    cnBondItem35.setUnitRef(ChbConstant.PURE);
		    cnBondItem35.setDecimals("7");
		    cnBondItemMap.put("CustodianFeeRate", cnBondItem35);
		    }
		    //募集起始日期（从）
		    if(sub.getRaisePstrdate() != null){
		    CnBondInterfItemDto cnBondItem36 = new CnBondInterfItemDto();
		    cnBondItem36.setValue(DateUtil.format(sub.getRaisePstrdate(), "yyyy-MM-dd"));
		    cnBondItemMap.put("SubscriptionStartDateEarliest", cnBondItem36);
		    }
		    //募集起始日期（到）
		    if(sub.getRaisePenddate() != null){
		    CnBondInterfItemDto cnBondItem37 = new CnBondInterfItemDto();
		    cnBondItem37.setValue(DateUtil.format(sub.getRaisePenddate(), "yyyy-MM-dd"));
		    cnBondItemMap.put("SubscriptionStartDateLatest", cnBondItem37);
		    }
		    //计划募集金额
		    if(sub.getRaiseAmtPlan() != null){
		    CnBondInterfItemDto cnBondItem38 = new CnBondInterfItemDto();
		    cnBondItem38.setValue(String.valueOf(sub.getRaiseAmtPlan().setScale(2, BigDecimal.ROUND_HALF_UP)));
		    cnBondItem38.setUnitRef(ChbConstant.币种全称_中债_人民币);
		    cnBondItem38.setDecimals("2");
		    cnBondItemMap.put("PlannedFundingAmountCNY", cnBondItem38);
		    }
		    //境内托管机构名称
		    if(StringUtil.isNotEmpty(sub.getInDepName())){
		    CnBondInterfItemDto cnBondItem39 = new CnBondInterfItemDto();
		    cnBondItem39.setValue(sub.getInDepName());
		    cnBondItemMap.put("DomesticCustodianNameOfPublicOrPrivateProduct", cnBondItem39);
		    }
		    //境内托管机构代码
		    if(StringUtil.isNotEmpty(sub.getInDepCode())){
		    CnBondInterfItemDto cnBondItem39_2 = new CnBondInterfItemDto();
		    cnBondItem39_2.setValue(sub.getInDepCode());
		    cnBondItemMap.put("DomesticCustodianIdentificationCode", cnBondItem39_2);
		    }
		    //境外托管机构国别
		    if(StringUtil.isNotEmpty(sub.getOutDepCtry())){
		    CnBondInterfItemDto cnBondItem40 = new CnBondInterfItemDto();
		    //子节点开始
		    List<Map<String, CnBondInterfItemDto>> mapList40 = new ArrayList<Map<String, CnBondInterfItemDto>>();
		    String[] outDepCtrys = sub.getOutDepCtry().split(ChbConstant.COMMA);
		    for(String str : outDepCtrys){
			    Map<String, CnBondInterfItemDto> cnBondItemMap40_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
			    CnBondInterfItemDto cnBondItem40_1 = new CnBondInterfItemDto();//第一个子节点
			    cnBondItem40_1.setValue(str);
			    cnBondItemMap40_1.put("OverseasCustodianNationality",cnBondItem40_1);
			    mapList40.add(cnBondItemMap40_1);	
		    }
		    cnBondItem40.setMaps(mapList40);//第一个父子节点组合结束
		    cnBondItemMap.put("OverseasCustodianNationalityTuple", cnBondItem40);
		    }
		    //境外托管机构名称
		    if(StringUtil.isNotEmpty(sub.getOutDepName())){
		    CnBondInterfItemDto cnBondItem41 = new CnBondInterfItemDto();
		    cnBondItem41.setValue(sub.getOutDepName());
		    cnBondItemMap.put("OverseasCustodianName", cnBondItem41);
		    }
		    //投资者风险偏好
		    if(StringUtil.isNotEmpty(sub.getRiskType())){
		    CnBondInterfItemDto cnBondItem42 = new CnBondInterfItemDto();
		    List<Map<String, CnBondInterfItemDto>> mapList42 = new ArrayList<Map<String, CnBondInterfItemDto>>();
		    //子节点开始
		    String [] riskTypes = sub.getRiskType().split(ChbConstant.COMMA);
		    for(String str : riskTypes){
			    Map<String, CnBondInterfItemDto> cnBondItemMap42_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
			    CnBondInterfItemDto cnBondItem42_1 = new CnBondInterfItemDto();//第一个子节点
			    cnBondItem42_1.setValue(str);
			    cnBondItemMap42_1.put("InvestorRiskPreference",cnBondItem42_1);
			    mapList42.add(cnBondItemMap42_1);
		    }
		    cnBondItem42.setMaps(mapList42);//第一个父子节点组合结束
		    cnBondItemMap.put("InvestorRiskPreferenceTuple", cnBondItem42);
		    }
		    //产品风险等级
		    if(StringUtil.isNotEmpty(sub.getRiskLevel())){
		    CnBondInterfItemDto cnBondItem43 = new CnBondInterfItemDto();
		    cnBondItem43.setValue(sub.getRiskLevel());
		    cnBondItemMap.put("ProductRiskRating", cnBondItem43);
		    }
		    //发型机构提前终止权标识
		    if(StringUtil.isNotEmpty(sub.getTermRightFlag())){
		    CnBondInterfItemDto cnBondItem44 = new CnBondInterfItemDto();
		    cnBondItem44.setValue(sub.getTermRightFlag());
		    cnBondItemMap.put("IssuerEarlyTerminationOptionPleaseSelect", cnBondItem44);
		    }
		    //客户赎回权标识
		    if(StringUtil.isNotEmpty(sub.getCusRedeemFlag())){
		    CnBondInterfItemDto cnBondItem45 = new CnBondInterfItemDto();
		    cnBondItem45.setValue(sub.getCusRedeemFlag());
		    cnBondItemMap.put("InvestorRedemptionOptionPleaseSelect", cnBondItem45);
		    }
		    //产品品牌
		    if(StringUtil.isNotEmpty(sub.getProdBrand())){
		    CnBondInterfItemDto cnBondItem46 = new CnBondInterfItemDto();
		    cnBondItem46.setValue(sub.getProdBrand());
		    cnBondItemMap.put("ProductBrand", cnBondItem46);
		    }

			/***1217升级**/
			//产品特殊属性
			if(StringUtil.isNotEmpty(sub.getPrdSpecProperty())){
				CnBondInterfItemDto cnBondItem3 = new CnBondInterfItemDto();
				//子节点开始
				List<Map<String, CnBondInterfItemDto>> cnBondItemMap3 = new ArrayList<Map<String, CnBondInterfItemDto>>();
				String [] prdSpecPropertys = sub.getPrdSpecProperty().split(ChbConstant.SEMICOLON);
				for(String str : prdSpecPropertys){
					Map<String, CnBondInterfItemDto> cnBondItemMap3_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
					CnBondInterfItemDto cnBondItem3_1 = new CnBondInterfItemDto();//第一个子节点
					cnBondItem3_1.setValue(str);
					cnBondItemMap3_1.put("ProductSpecialProperty",cnBondItem3_1);
					cnBondItemMap3.add(cnBondItemMap3_1);
				}
				cnBondItem3.setMaps(cnBondItemMap3);//第一个父子节点组合结束
				cnBondItemMap.put("ProductSpecialPropertyTuple", cnBondItem3);
			}
			/***1217升级**/

		    //产品期次
		    if(sub.getProdStage() != null){
		    CnBondInterfItemDto cnBondItem47 = new CnBondInterfItemDto();
		    cnBondItem47.setValue(String.valueOf(sub.getProdStage()));
		    cnBondItem47.setUnitRef(ChbConstant.PURE);
		    cnBondItem47.setDecimals("0");
		    cnBondItemMap.put("ProductTermNumber", cnBondItem47);
		    }
		    //投资管理费率%
		    if(sub.getInvestFeeRate() != null){
		    CnBondInterfItemDto cnBondItem48 = new CnBondInterfItemDto();
		    cnBondItem48.setValue(String.format("%.7f",sub.getInvestFeeRate()));
		    cnBondItem48.setUnitRef(ChbConstant.PURE);
		    cnBondItem48.setDecimals("7");
		    cnBondItemMap.put("InvestmentManagementFeeRate", cnBondItem48);
		    }
		    //合作模式
		    if(StringUtil.isNotEmpty(sub.getContMode())){
		    CnBondInterfItemDto cnBondItem49 = new CnBondInterfItemDto();
		    cnBondItem49.setValue(sub.getContMode());
		    cnBondItemMap.put("CooperationMode", cnBondItem49);
		    }
		    //合作机构名称
		    if(StringUtil.isNotEmpty(sub.getCooperationName())){
		    CnBondInterfItemDto cnBondItem50 = new CnBondInterfItemDto();
		    cnBondItem50.setValue(sub.getCooperationName());
		    cnBondItemMap.put("Cooperator", cnBondItem50);
		    }
		    //投资本金到账日
		    if(StringUtil.isNotEmpty(sub.getInvestPrinDate())){
		    CnBondInterfItemDto cnBondItem51 = new CnBondInterfItemDto();
		    cnBondItem51.setValue(sub.getInvestPrinDate());
		    cnBondItemMap.put("DueDateOfInvestedPrincipal", cnBondItem51);
		    }
		    //投资收益到账日
		    if(StringUtil.isNotEmpty(sub.getInvestProfDate())){
		    CnBondInterfItemDto cnBondItem52 = new CnBondInterfItemDto();
		    cnBondItem52.setValue(sub.getInvestProfDate());
		    cnBondItemMap.put("DueDateOfInvestmentIncome", cnBondItem52);
		    }
		    //产品增信标识
		    if(StringUtil.isNotEmpty(sub.getCreditFlag())){
		    CnBondInterfItemDto cnBondItem53 = new CnBondInterfItemDto();
		    cnBondItem53.setValue(sub.getCreditFlag());
		    cnBondItemMap.put("ProductCreditEnhancementPleaseSelect", cnBondItem53);
		    }
		    //产品增信机构类型
		    if(StringUtil.isNotEmpty(sub.getCreditType())){
		    CnBondInterfItemDto cnBondItem54 = new CnBondInterfItemDto();
		    //子节点开始
		    List<Map<String, CnBondInterfItemDto>> mapList54 = new ArrayList<Map<String, CnBondInterfItemDto>>();
		    String [] creditTypes = sub.getCreditType().split(ChbConstant.COMMA);
		    for(String str : creditTypes){
			    Map<String, CnBondInterfItemDto> cnBondItemMap54_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
			    CnBondInterfItemDto cnBondItem54_1 = new CnBondInterfItemDto();//第一个子节点
			    cnBondItem54_1.setValue(str);
			    cnBondItemMap54_1.put("TypeOfProductCreditEnhancementInstitution",cnBondItem54_1);
			    mapList54.add(cnBondItemMap54_1);
		    }
		    cnBondItem54.setMaps(mapList54);//第一个父子节点组合结束
		    cnBondItemMap.put("TypeOfProductCreditEnhancementInstitutionTuple", cnBondItem54);
		    }
		    //产品增信形式
		    if(StringUtil.isNotEmpty(sub.getCreditMode())){
		    CnBondInterfItemDto cnBondItem55 = new CnBondInterfItemDto();
		    cnBondItem55.setValue(sub.getCreditMode());
		    cnBondItemMap.put("MethodOfProductCreditEnhancement", cnBondItem55);
		    }
		    //投资资产种类及比例
		    if(StringUtil.isNotEmpty(sub.getInvTypeRat())){
		    CnBondInterfItemDto cnBondItem56 = new CnBondInterfItemDto();
		    cnBondItem56.setValue(sub.getInvTypeRat());
		    cnBondItemMap.put("InvestmentAssetTypeAndRatio", cnBondItem56);
		    }
		    //登记备注
		    if(StringUtil.isNotEmpty(sub.getRemark())){
		    CnBondInterfItemDto cnBondItem56 = new CnBondInterfItemDto();
		    cnBondItem56.setValue(sub.getRemark());
		    cnBondItemMap.put("FilingRegistrationElementsOfPublicOrPrivateProductDetails", cnBondItem56);
		    }
		    
		    //九大附件处理nineFile
		    if(StringUtil.isNotEmpty(sub.getNineFile())){
			    String [] nineFiles = sub.getNineFile().split(ChbConstant.SEMICOLON);
			    for (String oneFile : nineFiles){
			    	String [] str = oneFile.split(ChbConstant.COMMA);
			    	switch(str[0]){
			    	case "01"://报告主文件
					    CnBondInterfItemDto cnBondItem57 = new CnBondInterfItemDto();
					    //子节点开始
					    List<Map<String, CnBondInterfItemDto>> mapList57 = new ArrayList<Map<String, CnBondInterfItemDto>>();
					    for(int i = 1; i < str.length; i ++){
						    Map<String, CnBondInterfItemDto> cnBondItemMap57_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
						    CnBondInterfItemDto cnBondItem57_1 = new CnBondInterfItemDto();//第一个子节点
							String val = str[i];
							if(!val.startsWith(sub.getProdInsId()+ "_")){
								val = sub.getProdInsId()+ "_"+ val;
							}
						    cnBondItem57_1.setValue(val);
						    cnBondItemMap57_1.put("MainDocuments",cnBondItem57_1);
						    mapList57.add(cnBondItemMap57_1);
					    }
					    cnBondItem57.setMaps(mapList57);//第一个父子节点组合结束
					    cnBondItemMap.put("MainDocumentsTuple", cnBondItem57);
					    break;
					    
			    	case "02"://理财产品可行性评估报告
					    CnBondInterfItemDto cnBondItem58 = new CnBondInterfItemDto();
					    //子节点开始
					    List<Map<String, CnBondInterfItemDto>> mapList58 = new ArrayList<Map<String, CnBondInterfItemDto>>();
					    for(int i = 1; i < str.length; i ++){
						    Map<String, CnBondInterfItemDto> cnBondItemMap58_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
						    CnBondInterfItemDto cnBondItem58_1 = new CnBondInterfItemDto();//第一个子节点
							String val = str[i];
							if(!val.startsWith(sub.getProdInsId()+ "_")){
								val = sub.getProdInsId()+ "_"+ val;
							}
							cnBondItem58_1.setValue(val);
						    cnBondItemMap58_1.put("WealthManagementProductFeasibilityReports",cnBondItem58_1);
						    mapList58.add(cnBondItemMap58_1);
					    }
					    cnBondItem58.setMaps(mapList58);//第一个父子节点组合结束
					    cnBondItemMap.put("WealthManagementProductFeasibilityReportsTuple", cnBondItem58);
					    break;
					    
			    	case "03"://内部审核文件
					    CnBondInterfItemDto cnBondItem59 = new CnBondInterfItemDto();
					    //子节点开始
					    List<Map<String, CnBondInterfItemDto>> mapList59 = new ArrayList<Map<String, CnBondInterfItemDto>>();
					    for(int i = 1; i < str.length; i ++){
						    Map<String, CnBondInterfItemDto> cnBondItemMap59_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
						    CnBondInterfItemDto cnBondItem59_1 = new CnBondInterfItemDto();//第一个子节点
							String val = str[i];
							if(!val.startsWith(sub.getProdInsId()+ "_")){
								val = sub.getProdInsId()+ "_"+ val;
							}
							cnBondItem59_1.setValue(val);
						    cnBondItemMap59_1.put("InternalAuthorizedDocuments",cnBondItem59_1);
						    mapList59.add(cnBondItemMap59_1);
					    }
					    cnBondItem59.setMaps(mapList59);//第一个父子节点组合结束
					    cnBondItemMap.put("InternalAuthorizedDocumentsTuple", cnBondItem59);
					    break;
			    		
			    	case "04"://尽职调查文件
					    CnBondInterfItemDto cnBondItem60 = new CnBondInterfItemDto();
					    //子节点开始
					    List<Map<String, CnBondInterfItemDto>> mapList60 = new ArrayList<Map<String, CnBondInterfItemDto>>();
					    for(int i = 1; i < str.length; i ++){
						    Map<String, CnBondInterfItemDto> cnBondItemMap60_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
						    CnBondInterfItemDto cnBondItem60_1 = new CnBondInterfItemDto();//第一个子节点
							String val = str[i];
							if(!val.startsWith(sub.getProdInsId()+ "_")){
								val = sub.getProdInsId()+ "_"+ val;
							}
							cnBondItem60_1.setValue(val);
						    cnBondItemMap60_1.put("DueDiligenceDocumentsOnRelevantParties",cnBondItem60_1);
						    mapList60.add(cnBondItemMap60_1);
					    }
					    cnBondItem60.setMaps(mapList60);//第一个父子节点组合结束
					    cnBondItemMap.put("DueDiligenceDocumentsOnRelevantPartiesTuple", cnBondItem60);
					    break;
					    
			    	case "05"://法律文件
					    CnBondInterfItemDto cnBondItem61 = new CnBondInterfItemDto();
					    //子节点开始
					    List<Map<String, CnBondInterfItemDto>> mapList61 = new ArrayList<Map<String, CnBondInterfItemDto>>();
					    for(int i = 1; i < str.length; i ++){
						    Map<String, CnBondInterfItemDto> cnBondItemMap61_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
						    CnBondInterfItemDto cnBondItem61_1 = new CnBondInterfItemDto();//第一个子节点
							String val = str[i];
							if(!val.startsWith(sub.getProdInsId()+ "_")){
								val = sub.getProdInsId()+ "_"+ val;
							}
							cnBondItem61_1.setValue(val);
						    cnBondItemMap61_1.put("LegalDocumentsSignedByRelevantParties",cnBondItem61_1);
						    mapList61.add(cnBondItemMap61_1);
					    }
					    cnBondItem61.setMaps(mapList61);//第一个父子节点组合结束
					    cnBondItemMap.put("LegalDocumentsSignedByRelevantPartiesTuple", cnBondItem61);
					    break;
					    
			    	case "06"://理财产品销售文件
					    CnBondInterfItemDto cnBondItem62 = new CnBondInterfItemDto();
					    //子节点开始
					    List<Map<String, CnBondInterfItemDto>> mapList62 = new ArrayList<Map<String, CnBondInterfItemDto>>();
					    for(int i = 1; i < str.length; i ++){
						    Map<String, CnBondInterfItemDto> cnBondItemMap62_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
						    CnBondInterfItemDto cnBondItem62_1 = new CnBondInterfItemDto();//第一个子节点
							String val = str[i];
							if(!val.startsWith(sub.getProdInsId()+ "_")){
								val = sub.getProdInsId()+ "_"+ val;
							}
							cnBondItem62_1.setValue(val);
						    cnBondItemMap62_1.put("WealthManagementProductSalesDocuments",cnBondItem62_1);
						    mapList62.add(cnBondItemMap62_1);
					    }
					    cnBondItem62.setMaps(mapList62);//第一个父子节点组合结束
					    cnBondItemMap.put("WealthManagementProductSalesDocumentsTuple", cnBondItem62);
					    break;
					    
			    	case "07"://理财产品说明书
					    CnBondInterfItemDto cnBondItem63 = new CnBondInterfItemDto();
					    //子节点开始
					    List<Map<String, CnBondInterfItemDto>> mapList63 = new ArrayList<Map<String, CnBondInterfItemDto>>();
					    for(int i = 1; i < str.length; i ++){
						    Map<String, CnBondInterfItemDto> cnBondItemMap63_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
						    CnBondInterfItemDto cnBondItem63_1 = new CnBondInterfItemDto();//第一个子节点
							String val = str[i];
							if(!val.startsWith(sub.getProdInsId()+ "_")){
								val = sub.getProdInsId()+ "_"+ val;
							}
							cnBondItem63_1.setValue(val);
						    cnBondItemMap63_1.put("WealthManagementProductSpecifications",cnBondItem63_1);
						    mapList63.add(cnBondItemMap63_1);
					    }
					    cnBondItem63.setMaps(mapList63);//第一个父子节点组合结束
					    cnBondItemMap.put("WealthManagementProductSpecificationsTuple", cnBondItem63);
					    break;
					    
			    	case "08"://理财产品宣传材料
					    CnBondInterfItemDto cnBondItem64 = new CnBondInterfItemDto();
					    //子节点开始
					    List<Map<String, CnBondInterfItemDto>> mapList64 = new ArrayList<Map<String, CnBondInterfItemDto>>();
					    for(int i = 1; i < str.length; i ++){
						    Map<String, CnBondInterfItemDto> cnBondItemMap64_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
						    CnBondInterfItemDto cnBondItem64_1 = new CnBondInterfItemDto();//第一个子节点
							String val = str[i];
							if(!val.startsWith(sub.getProdInsId()+ "_")){
								val = sub.getProdInsId()+ "_"+ val;
							}
							cnBondItem64_1.setValue(val);
						    cnBondItemMap64_1.put("WealthManagementProductMarketingDocuments",cnBondItem64_1);
						    mapList64.add(cnBondItemMap64_1);
					    }
					    cnBondItem64.setMaps(mapList64);//第一个父子节点组合结束
					    cnBondItemMap.put("WealthManagementProductMarketingDocumentsTuple", cnBondItem64);
					    break;
					    
			    	case "09"://其他材料
					    CnBondInterfItemDto cnBondItem65 = new CnBondInterfItemDto();
					    //子节点开始
					    List<Map<String, CnBondInterfItemDto>> mapList65 = new ArrayList<Map<String, CnBondInterfItemDto>>();
					    for(int i = 1; i < str.length; i ++){
						    Map<String, CnBondInterfItemDto> cnBondItemMap65_1 =  new LinkedHashMap<String, CnBondInterfItemDto>();//子节点map
						    CnBondInterfItemDto cnBondItem65_1 = new CnBondInterfItemDto();//第一个子节点
							String val = str[i];
							if(!val.startsWith(sub.getProdInsId()+ "_")){
								val = sub.getProdInsId()+ "_"+ val;
							}
							cnBondItem65_1.setValue(val);
						    cnBondItemMap65_1.put("OtherDocuments",cnBondItem65_1);
						    mapList65.add(cnBondItemMap65_1);
					    }
					    cnBondItem65.setMaps(mapList65);//第一个父子节点组合结束
					    cnBondItemMap.put("OtherDocumentsTuple", cnBondItem65);
					    break;
					    
					default:
						break;
			    	}
			    }
		    }
		    //登记备注
		    if(StringUtil.isNotEmpty(sub.getSumitSeq())){
		    CnBondInterfItemDto cnBondItem62 = new CnBondInterfItemDto();
		    cnBondItem62.setValue(sub.getSumitSeq());
		    cnBondItemMap.put("RegistrationSerialNumber", cnBondItem62);
		    }

  }
}
