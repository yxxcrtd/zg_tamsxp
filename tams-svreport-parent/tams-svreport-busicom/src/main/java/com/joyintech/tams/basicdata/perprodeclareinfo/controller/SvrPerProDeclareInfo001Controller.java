package com.joyintech.tams.basicdata.perprodeclareinfo.controller;


import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.*;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.system.constant.SystemGlobalConstant;
import com.joyintech.tams.basicdata.perprodeclareinfo.dto.SvrPerProDeclareInfo001Dto;
import com.joyintech.tams.basicdata.perprodeclareinfo.service.SvrPerProDeclareInfoService001;
import com.joyintech.tams.basicdata.perprodeclareinfo.util.ErrdataConvertUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 产品申报登记信息
 *
 * @author yxj
 * @date 1.0 2022年03月07日
 * @since 1.0
 */
@Controller
@PageId("SvrPerProDeclareInfo001")
@RequestMapping("/basicdata/perprodeclareinfo/SvrPerProDeclareInfo001")
public class SvrPerProDeclareInfo001Controller extends BaseController {

    @Autowired
    private SvrPerProDeclareInfoService001 svrPerProDeclareInfo001Service;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初始化
     * @param model
     * @return
     */
    @Override
    public String init(Model model) {
        this.setSelectOptionToModel(model, DictCode.产品期限, DictCode.产品运作模式, DictCode.报送数据状态,DictCode.维护数据状态);
        return super.init(model);
    }

    /**
     * 页面查询
     * @return page
     */
    @ResponseBody
    @PostMapping("selectForPage")
    public Page<SvrPerProDeclareInfo001Dto> selectForPage() {
        return svrPerProDeclareInfo001Service.selectForPage(RequestUtil.getPageParam());
    }

    /**
     * 产品名称 联想下拉框
     * @return
     */
    @ResponseBody
    @RequestMapping("queryProdInfo")
    public List<SuggestDto> queryProdInfo(){
        return svrPerProDeclareInfo001Service.queryProdInfo(RequestUtil.getPageParam());
    }

    /**
     * 删除check
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody SvrPerProDeclareInfo001Dto dto) {
        AjaxResult ar =new AjaxResult();
        List<ValidMessage> errorList = ar.getErrorList();
        if (StringUtil.equals(dto.getStatus(), DictCodeListExt1.维护数据状态_删除.getValue())) {
            errorList.add(new ValidMessage("数据状态为删除的数据不允许修改！"));
            ar.setErrorList(errorList);
        }
        if(dto.getSelectedItem() != null && (!dto.getSelectedItem().isEmpty())){
            for (SvrPerProDeclareInfo001Dto entity : dto.getSelectedItem()) {
                if (StringUtil.equals(entity.getStatus(), DictCodeListExt1.维护数据状态_删除.getValue())){
                    errorList.add(new ValidMessage("数据状态为删除的数据不允许修改！"));
                    ar.setErrorList(errorList);
                }
            }
        }
        return ar;
    }

    /**
     * 多行删除
     * @param map
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("deleteList")
    public AjaxResult deleteList(@RequestBody Map<String,Object> map) throws Exception {
        Map<String, Object> pageParam = RequestUtil.getPageParam();
        if (map.get("datas") != null && !map.get("datas").equals("")) {
             svrPerProDeclareInfo001Service.deleteList(map);
            return new AjaxResult("删除成功");
        }
        return new AjaxResult("删除失败");
    }


    /**
     * excel 导出
     * @return
     * @throws Exception
     */
    @RequestMapping(ACTION_EXPORT)
    public ResponseEntity<byte[]> export()
            throws Exception {
        Map<String, Object> params= RequestUtil.getPageParam();
        List<SvrPerProDeclareInfo001Dto> list = Lists.newArrayList();
        if(StringUtils.isNotEmpty((String)params.get("selectData"))){
            List<Map> selectData = JSONArray.parseArray((String) params.get("selectData"), Map.class);
            List<String> declareUuids = selectData.stream().map(e -> (String)e.get("declareUuid")).collect(Collectors.toList());
            list = svrPerProDeclareInfo001Service.selectByKeys(declareUuids);
        }else {
            list = svrPerProDeclareInfo001Service.queryAllList(params);
        }
        List<SvrPerProDeclareInfo001Dto> listdto = Lists.newArrayList();
        if(list.size()>0){
            for (int k=0;k<list.size();k++){
                if(list.get(k).getStatusValid().equals("A")){
                    SvrPerProDeclareInfo001Dto dto = ErrdataConvertUtil.errdataConvert(SvrPerProDeclareInfo001Dto.class, list.get(k).getOrgData());
                    dto.setStatusValid("A");
                    dto.setErrMsg(list.get(k).getErrMsg());
                    listdto.add(dto);
                }else{
                    listdto.add(list.get(k));
                }
            }
        }
        File outFile = svrPerProDeclareInfo001Service.makeFile(
                "/com/joyintech/tams/basicdata/perprodeclareinfo/conf/export_conf.xml",
                listdto);
        return ExportUtil.export(
                "产品申报登记信息维护" + SystemGlobalConstant.OPEN_PARENTHESIS
                        + DateUtil.format(DateUtil.platDateFromCache())
                        + SystemGlobalConstant.CLOSE_PARENTHESIS
                        + SystemGlobalConstant.POINT
                        + FileNameUtil.getFileExtName(outFile),
                outFile, true);
    }

    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    @ResponseBody
    @PostMapping("checkConfirmMsgForImportData")
    public AjaxResult checkConfirmMsgForImportData(String fileName) throws Exception {
        return svrPerProDeclareInfo001Service.checkConfirmMsgForImportData(fileName);
    }


    /**
     * 导入数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    @ResponseBody
    @RequestMapping("importData")
    public List<ExcelValidDto> importFile(String fileName) throws Exception {
        return svrPerProDeclareInfo001Service.importData(fileName);
    }
    /**
     *  删除
     * @param dto 页面选中的数据
     * @param br br
     * @return 结果
     * @throws Exception 异常
     */
    @ResponseBody
    @RequestMapping(ACTION_DELETE)
    public AjaxResult delete(@RequestBody @Validated({
            ValidateGroupDelete.class}) SvrPerProDeclareInfo001Dto dto, BindingResult br)
            throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        ProcessParameter<SvrPerProDeclareInfo001Dto> processParameter = new ProcessParameter<SvrPerProDeclareInfo001Dto>();
        WorkFlowParameter<SvrPerProDeclareInfo001Dto> params = new WorkFlowParameter<SvrPerProDeclareInfo001Dto>();
        params.setPageId("SvrPerProDeclareInfo002");
        params.setElementId(dto.getElementId());
        params.setBusinessCode(item -> item.getProdInsId());
        params.setBusinessType(DictCodeList.共通业务类型_产品申报信息.getValue());
        // 设置流程业务说明, entity即传入的dto
        params.setComment(entity -> this.getMessage("SvrPerProDeclareInfo002.prodInsId") + "("
                + entity.getProdInsId() + ")"
        );
        processParameter.setWorkFlowParameter(params);
        dto.getSelectedItem().forEach(e->{
            e.setCreateUser(CommonUtil.getUserName());
            e.setCreateTime(DateUtil.now());
            e.setUpdateUser(CommonUtil.getUserName());
            e.setUpdateTime(DateUtil.now());
        });
        String msg = svrPerProDeclareInfo001Service.start(processParameter,dto.getSelectedItem().toArray(new SvrPerProDeclareInfo001Dto[dto.getSelectedItem().size()]));
        return new AjaxResult(msg);

    }

    @ResponseBody
    @RequestMapping(value = "/deltFile", method = RequestMethod.POST)
    public AjaxResult deltFile(@RequestParam Map<String, Object> map)  {

        svrPerProDeclareInfo001Service.deltFile(map);
        return new AjaxResult("删除成功");
    }
    @ResponseBody
    @RequestMapping(value = "/getFileList", method = RequestMethod.POST)
    public String getFileList(@RequestParam Map<String, Object> map)  {
        String res;
        try {
            res = svrPerProDeclareInfo001Service.getFileList(map);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
        return res;

    }
}
