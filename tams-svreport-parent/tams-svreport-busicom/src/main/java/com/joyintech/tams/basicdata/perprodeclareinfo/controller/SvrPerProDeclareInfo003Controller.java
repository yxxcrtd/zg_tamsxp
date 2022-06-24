package com.joyintech.tams.basicdata.perprodeclareinfo.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.system.common.listener.UploadListener;
import com.joyintech.tams.basicdata.perprodeclareinfo.dto.ChbPrdDeclareInfoFileUploadDto;
import com.joyintech.tams.basicdata.perprodeclareinfo.dto.SvrPerProDeclareInfo001Dto;
import com.joyintech.tams.basicdata.perprodeclareinfo.dto.UploadFileInfo;
import com.joyintech.tams.basicdata.perprodeclareinfo.service.SvrPerProDeclareInfoService001;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dto.ChbPrdDeclareInfoDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import com.joyintech.workflow.engine.exception.WorkFlowException;
import com.joyintech.workflow.engine.util.WorkFlowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品申报登记信息
 *
 * @author yxj
 * @date 1.0 2022年03月07日
 * @since 1.0
 */
@Controller
@PageId("SvrPerProDeclareInfo003")
@RequestMapping("/basicdata/perprodeclareinfo/SvrPerProDeclareInfo003")
public class SvrPerProDeclareInfo003Controller extends BaseController {
    @Autowired
    private SvrPerProDeclareInfoService001 svrPerProDeclareInfo00rvice;
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model){
        String dataArrStr = getParam("dataArrStr");
        if(StringUtil.isNotEmpty(dataArrStr)){
            model.addAttribute("dataArrStr", dataArrStr);
        }
        return super.init(model);
    }


    /**
     * 十大附件批量上传校验
     * @param map  入参
     * @return 处理结果
     * @throws IOException 异常信息
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody ChbPrdDeclareInfoFileUploadDto dto) throws IOException {
        AjaxResult res=new AjaxResult();
        try {
            List<String> prodInsIdList=new ArrayList<String>();
            for(SvrPerProDeclareInfo001Dto svrPerProDeclareInfo001Dto:dto.getUploadArrStr()){
                prodInsIdList.add(svrPerProDeclareInfo001Dto.getProdInsId());
            }
            List<Map<String, Object>> fileList=new ArrayList<>();
            List<UploadFileInfo> fileArray=new ArrayList<UploadFileInfo>();
            //获取所有上传附件
            fileArray=dto.getFileArray();
            for(UploadFileInfo file:fileArray){
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("srcName", (String)file.getSrcName());
                map.put("fileNo", (String)file.getFileNo());
                map.put("targetFileSTR", (String)file.getTargetFile());
                map.put("prodInsIdList", prodInsIdList);
                fileList.add(map);
            }
            res=svrPerProDeclareInfo00rvice.tenFileUploadBatch(fileList);

        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return res;

    }

}
