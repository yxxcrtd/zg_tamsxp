package com.joyintech.tams.invest.risk.valtblimport.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.base.intf.DataDict;
import com.joyintech.framework.base.intf.DataDictItem;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.RskOutValtblImportHisMapper;
import com.joyintech.tams.common.base.dao.ValtblImportDateTraceMapper;
import com.joyintech.tams.common.base.dao.ValtblImportHisTraceMapper;
import com.joyintech.tams.common.base.entity.ChbAssetInfo;
import com.joyintech.tams.common.base.entity.RskOutValtblImportHis;
import com.joyintech.tams.common.base.entity.RskOutValtblImportHisKey;
import com.joyintech.tams.common.base.entity.ValtblImportDateTrace;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalrelateimport.service.RskValRelateImport001Service;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 估值表导入通用方法
 *
 * @author swwan
 * @className RskValTableImportCommonService
 * @date 2021/6/15 17:01
 */
@Service
public class RskValTableImportCommonService extends BaseService {
    /**
     * RskValRelateImport001Service
     */
    @Autowired
    private RskValRelateImport001Service rskValRelateImport001Service;

    @Autowired
    private RskOutValtblImportHisMapper rskOutValtblImportHisMapper;

    @Autowired
    private ValtblImportHisTraceMapper valtblImportHisTraceMapper;
    @Autowired
    private ValtblImportDateTraceMapper valtblImportDateTraceMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());
    public List<ChbAssetInfo> queryAssetInfo() {
        return restTemplate.getForList(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValRelateImport/queryAssetInfo",
                ChbAssetInfo.class);
    }

    /**
     * 根据组合名称获取组合信息
     *
     * @param assetName 资产名称
     * @return SelectOptionDto 资产信息
     */
    public SelectOptionDto getAssetInfoFromName(String assetName) {
        List<SelectOptionDto> selectOptionDtoList =rskValRelateImport001Service.queryProdIdInfo(RequestUtil.getPageParam());
        if (StringUtil.isNotEmpty(assetName)) {
            if (assetName.contains(File.separator)) {
                String[] arr = StringUtil.split(assetName, File.separator);
                assetName = arr[arr.length - 1];
            }
            String finalAssetName = assetName;
            List<SelectOptionDto> list = selectOptionDtoList.stream().filter((assetInfo) -> (StringUtil.equals(finalAssetName,assetInfo.getText()) || StringUtil.equals(finalAssetName,assetInfo.getId()))).collect(Collectors.toList());
            if (!StringUtil.isEmptyList(list)) {
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 获取估值表日期
     *
     * @param dateStr 日期字符
     * @return Date 估值日期
     */
    public Date getDateFromStr(String dateStr) {
        try {
            if (StringUtil.isNotEmpty(dateStr)) {
                if (dateStr.contains(GlobalConstant.UNDER_LINE)) {
                    String[] arr = StringUtil.split(dateStr, GlobalConstant.UNDER_LINE);
                    dateStr = arr[0];
                }
                if (dateStr.endsWith(".xls")) {
                    dateStr = dateStr.substring(0, dateStr.length() - 4);
                } else if (dateStr.endsWith(".xlsx")) {
                    dateStr = dateStr.substring(0, dateStr.length() - 5);
                }
                if (dateStr.length() == 8 && DateUtil.isDateStrE(dateStr)) {
                    return DateUtil.parse(dateStr);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取交易场所
     *
     * @param tradePlaceStr 交易场所
     * @return String 交易场所
     */
    public String getTradePlaceFromStr(String tradePlaceStr) {
        if ("1".equals(tradePlaceStr) || tradePlaceStr.contains("SH") || tradePlaceStr.contains("上交所")) {
            return DictCodeListExt2.交易流通场所_中债_上海证券交易所.getValue();
        } else if ("2".equals(tradePlaceStr) || tradePlaceStr.contains("SZ") || tradePlaceStr.contains("深交所")) {
            return DictCodeListExt2.交易流通场所_中债_深圳证券交易所.getValue();
        } else if ("3".equals(tradePlaceStr) || tradePlaceStr.contains("IB") || tradePlaceStr.contains("CY") || tradePlaceStr.contains("银行间")) {
            return DictCodeListExt2.交易流通场所_中债_银行间债券市场.getValue();
        } else if ("4".equals(tradePlaceStr) || tradePlaceStr.contains("OTC") || tradePlaceStr.contains("OF") || tradePlaceStr.contains("场外")) {
            return DictCodeListExt2.交易流通场所_中债_基金市场.getValue();
        } else if ("5".equals(tradePlaceStr) || tradePlaceStr.contains("其他")) {
            return DictCodeListExt2.交易流通场所_中债_其他交易流通场所.getValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取期限品种
     *
     * @param subCodeStr
     * @param subNameStr
     * @return String 期限品种
     */
    public String getTermTypeFromStr(String subCodeStr, String subNameStr) {
        Map<String, DataDict> dataDictMap = CacheUtil.getAllDictCode();
        List<DataDictItem> dictItemList = new ArrayList<>();

        if (null != dataDictMap) {
            DataDict dto = dataDictMap.get(DictCode.回购期限品种.getValue());
            if (null != dto) {
                dictItemList = dto.getItems().stream().filter(item ->
                        StringUtil.equals(DictCode.回购期限品种.getValue(), item.getDictCode())).collect(Collectors.toList());
            }
        }

        if (StringUtil.isNotEmpty(subCodeStr)) {
            for (DataDictItem item : dictItemList) {
                if (subCodeStr.contains(item.getItemCode()) || subCodeStr.contains(item.getItemValue())) {
                    return item.getItemCode();
                }
            }
        }

        if (StringUtil.isNotEmpty(subNameStr)) {
            for (DataDictItem item : dictItemList) {
                if (subNameStr.contains(item.getItemCode()) || subNameStr.contains(item.getItemValue())) {
                    return item.getItemCode();
                }
            }
        }

        return null;
    }

    /**
     * 解压zip文件
     *
     * @param originFile
     * @return boolean
     */
    public static boolean decompressZip(File originFile, List<File> fileList) {
        boolean flag = true;
        if (!StringUtil.equals("zip", FileNameUtil.getFileExtName(originFile))) {
            return false;
        }
        String targetDir = getUnzipPathFromFile(originFile);
        ZipFile zipFile = null;//zip
        OutputStream outputStream = null; //输出流
        InputStream inputStream = null;//输入流
        try {
            zipFile = new ZipFile(originFile, "GBK");
            ZipEntry zipEntry = null;
            Enumeration<ZipEntry> entry = zipFile.getEntries();
            while (entry.hasMoreElements()) {
                zipEntry = entry.nextElement();
                String fileName = zipEntry.getName();
                File outputFile = new File(targetDir + fileName);
                if (zipEntry.isDirectory()) {
                    FileUtils.forceMkdir(outputFile);
                    continue;
                } else if (!outputFile.getParentFile().exists()) {
                    FileUtils.forceMkdir(outputFile.getParentFile());
                }
                fileList.add(outputFile);
                outputStream = new FileOutputStream(outputFile);
                inputStream = zipFile.getInputStream(zipEntry);
                int len;
                byte[] buffer = new byte[8192];
                while (-1 != (len = inputStream.read(buffer))) {
                    outputStream.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception ex) {
                }

            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception ex) {
                }

            }
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (Exception ex) {
                }

            }
        }
        return true;
    }

    /**
     * 获取解压目录
     *
     * @param file
     * @return boolean
     */
    public static String getUnzipPathFromFile(File file) {
        String outPath = file.getPath().substring(0,file.getPath().lastIndexOf("\\") + 1);
        return outPath + file.getName().substring(0, file.getName().lastIndexOf(".zip")) + File.separator;
    }

    /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFile(String directoryPath,boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(isAddDirectory){
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }
    public void saveRskOutValtblImportHis(String name,String path,String ptlId,String portfolioName,Date resValDate,
                                           String importType,String errorCode,Date startDate,Date impdate,String deptCode,String resultInfo) {
        //保存委托估值表接入记录
        RskOutValtblImportHis dto = new RskOutValtblImportHis();
        dto.setFileName(name);
        dto.setFilePath(path);
        dto.setPortfolioId(ptlId);
        dto.setPortfolioName(portfolioName);
        dto.setValdate(resValDate);
        dto.setImpdate(impdate);
        dto.setImportType(importType);
        dto.setErrorCode(errorCode);
        dto.setStartTime(startDate);
        dto.setEndTime(DateUtil.now());
        dto.setResultInfo(resultInfo);
        dto.setTimedTaskStatus("S");
        dto.setCreateDept(deptCode);
        RskOutValtblImportHisKey key=new RskOutValtblImportHisKey();
        key.setFileName(dto.getFileName());
        key.setPortfolioName(portfolioName);
        key.setValdate(dto.getValdate());
        RskOutValtblImportHis rskOutValtblImportHisDto = rskOutValtblImportHisMapper.selectByPrimaryKey(key);
        if(rskOutValtblImportHisDto!=null || StringUtil.isNotEmpty(rskOutValtblImportHisDto)){
            rskOutValtblImportHisMapper.updateByPrimaryKey(dto);
        }else{
            rskOutValtblImportHisMapper.insertSelective(dto);
        }
        valtblImportHisTraceMapper.insert(dto);
        //处理成功的文件入库估值表导入日期记录表
        if (StringUtil.equals(dto.getImportType(), DictCodeListExt1.估值表自动解析处理状态_正常结束.getValue())){
            ValtblImportDateTrace dateDto = new ValtblImportDateTrace();
            dateDto.setUuid(IDGenerator.generate());
            dateDto.setImpdate(dto.getImpdate());
            dateDto.setPortfolioId(dto.getPortfolioId());
            dateDto.setValdate(dto.getValdate());
            dateDto.setCreateDept(deptCode);
            valtblImportDateTraceMapper.insert(dateDto);
        }
    }

}



