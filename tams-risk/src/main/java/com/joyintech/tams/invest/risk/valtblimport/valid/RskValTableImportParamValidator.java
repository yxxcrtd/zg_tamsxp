/*
 * 文件名：RskValTableImportParamValidator.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月29日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.valtblimport.valid;

import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.invest.common.constant.BussinessConstant;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValTableImportParamDto;
import com.joyintech.tams.invest.risk.valtblimport.service.RskValTableImportCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *  估值表预览参数验证
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月29日
 * @see RskValTableImportParamValidator
 * @since 1.0
 */
@Component
public class RskValTableImportParamValidator implements BeanValidator{

    @Autowired
    private RskValTableImportCommonService rskValTableImportCommonService;

    @Override
    public List<ValidMessage> valid(Object value) {
        RskValTableImportParamDto dto = (RskValTableImportParamDto)value;
        ArrayList<ValidMessage> resultList = new ArrayList<>();
        if(StringUtil.isEmpty(dto.getValTableFileName())){
            resultList.add(new ValidMessage("valTableFileName",
                    getMessage("COMMON_E_0057", getMessage("RskValTableImport001.valTableFileName"))));
        } else {
            File file = new SafelyFile(dto.getValTableFileName());
            if (dto.getValTableFileName().endsWith(".zip")) {
                List<File> fileList = new ArrayList<>();
                if (Objects.nonNull(file)) {
                    if (RskValTableImportCommonService.decompressZip(file, fileList)) {
                        if (!StringUtil.isEmptyList(fileList)) {
                            for (File file1 : fileList) {
                                checkFileName(file1.getName(), resultList);
                            }
                        } else {
                            resultList.add(new ValidMessage("valTableFileName", "上传的估值表zip文件为空!"));
                        }
                    }
                } else {
                    resultList.add(new ValidMessage("valTableFileName", "上传估值表zip文件失败!"));
                }

            } else {
                checkFileName(file.getName(), resultList);
                if(StringUtil.isEmpty(dto.getPtlId())){
                    resultList.add(new ValidMessage("ptlId",
                            getMessage("COMMON_E_0057", getMessage("RskValTableImport001.ptlId"))));
                }
                if(null==dto.getValDate()){
                    resultList.add(new ValidMessage("valDate",
                            getMessage("COMMON_E_0057", getMessage("RskValTableImport001.valDate"))));
                }
            }
        }
        return resultList;
    }

    private void checkFileName(String fileName, ArrayList<ValidMessage> resultList) {
        String chbValSplitStr = CacheUtil.getSysParam(BussinessConstant.CHB_VAL_SPLIT_STR);
        String[] arr = fileName.split(chbValSplitStr);
        if (arr.length != 2) {
            resultList.add(new ValidMessage("valTableFileName", "估值表导入失败，无法预览"));
        } else {
            Date valDate = rskValTableImportCommonService.getDateFromStr(arr[1]);
            if (Objects.isNull(valDate)) {
                resultList.add(new ValidMessage("valTableFileName", "估值表导入失败，无法预览"));
            }
            SelectOptionDto info = rskValTableImportCommonService.getAssetInfoFromName(arr[0]);
            if (Objects.isNull(info)) {
                resultList.add(new ValidMessage("valTableFileName", "估值表导入失败，无法预览"));
            }
        }
    }
}
