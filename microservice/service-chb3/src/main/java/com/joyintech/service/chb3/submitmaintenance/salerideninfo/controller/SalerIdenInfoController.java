package com.joyintech.service.chb3.submitmaintenance.salerideninfo.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.dto.SalerIdenInfoDto;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.service.SalerIdenInfo001Service;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.service.SalerIdenInfo002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/SalerIdenInfo")
public class SalerIdenInfoController {

    @Autowired
    private SalerIdenInfo001Service salerIdenInfo001Service;

    @Autowired
    private SalerIdenInfo002Service salerIdenInfo002Service;

    /**
     * 分页查询列表
     *
     * @return Page
     */
    @PostMapping("/findPage/{page}/{length}")
    public Page<SalerIdenInfoDto> findPage(@RequestBody Map<String, Object> pageParam, @PathVariable("page") int page, @PathVariable("length") int length)
    {
        return salerIdenInfo001Service.selectForPage(page,length,pageParam);
    }

    /**
     *取值设置页面
     * selectDetail
     */
    @PostMapping("selectDetail")
    public SalerIdenInfoDto selectDetail(@RequestBody Map<String, Object> param) throws Exception{
        return  salerIdenInfo002Service.selectDetail(param);
    }

    /**
     * 新建保存
     * @param dto
     */
    @PostMapping("saveAndSubmit")
    public void saveAndSubmit(@RequestBody SalerIdenInfoDto dto) {
        salerIdenInfo002Service.saveAndSubmit(dto);
    }

    /**
     * 修改保存
     * @param dto
     */
    @PostMapping("update")
    public void update(@RequestBody SalerIdenInfoDto dto) {
        salerIdenInfo002Service.update(dto);
    }

    /**
     * 导出数据查询
     *
     * @return ResponseEntity<byte[]>
     * @throws Exception ExportUtil.export
     * @see
     * @since 1.0
     */
    @PostMapping("/queryAllList")
    public List<SalerIdenInfoDto> queryAllList(@RequestBody Map<String, Object> params) throws Exception{
        return salerIdenInfo001Service.queryAllList(params);
    }

    /**
     * 删除
     * @param dto
     */
    @PostMapping("deleteByPrimaryKey")
    public void deleteByPrimaryKey(@RequestBody SalerIdenInfoDto dto) {
        salerIdenInfo001Service.deleteByPrimaryKey(dto);
    }

    /**
     * 导入数据文件之前检查数据
     *
     * @param param 导入的文件名
     * @return 处理结果
     */
    @SuppressWarnings("unchecked")
    @PostMapping("checkConfirmMsgForImportData")
    public AjaxResult checkConfirmMsgForImportData(@RequestBody Map<String, Object> param)  {
        List<List<String>> dataList = (List<List<String>>) param.get("dataList");
        String fileName = (String) param.get("fileName");
        return salerIdenInfo001Service.checkConfirmMsgForImportData(dataList,fileName);
    }

    /**
     * 导入数据文件
     *
     * @param param 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    @PostMapping("importData")
    public List<ExcelValidDto> importData(@RequestBody Map<String, Object> param) {
        List<List<String>> dataList = (List<List<String>>) param.get("dataList");
        List<List<String>> tempList = (List<List<String>>) param.get("tempList");
        String userName=param.get("userName")==null?null:param.get("userName").toString();
        String orgCode=param.get("orgCode")==null?null:param.get("orgCode").toString();
        String deptCode=param.get("deptCode")==null?null:param.get("deptCode").toString();
        return salerIdenInfo001Service.importData(dataList,tempList,userName,orgCode,deptCode);
    }

    /**
     * 导入销售人员登记编码之前检查数据
     *
     * @param param 导入的文件名
     * @return 处理结果
     */
    @SuppressWarnings("unchecked")
    @PostMapping("checkConfirmMsgForImportCodeData")
    public AjaxResult checkConfirmMsgForImportCodeData(@RequestBody Map<String, Object> param)  {
        List<List<String>> dataList = (List<List<String>>) param.get("dataList");
        String fileName = (String) param.get("fileName");
        return salerIdenInfo001Service.checkConfirmMsgForImportCodeData(dataList,fileName);
    }

    /**
     * 导入销售人员登记编码
     *
     * @param param 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    @PostMapping("importCode")
    public List<ExcelValidDto> importCode(@RequestBody Map<String, Object> param) {
        List<List<String>> dataList = (List<List<String>>) param.get("dataList");
        List<List<String>> tempList = (List<List<String>>) param.get("tempList");
        String userName=param.get("userName")==null?null:param.get("userName").toString();
        String orgCode=param.get("orgCode")==null?null:param.get("orgCode").toString();
        String deptCode=param.get("deptCode")==null?null:param.get("deptCode").toString();
        return salerIdenInfo001Service.importCode(dataList,tempList,userName,orgCode,deptCode);
    }

    /**
     * 理财销售人员姓名联想框
     */
    @PostMapping("getSalerName")
    public List<SuggestDto> getSalerName(@RequestBody Map<String, Object> map) {
        return salerIdenInfo001Service.getSalerName(map);
    }
    /**
     * 查询发行机构代码
     * @param orgCode
     * @return
     */
    @PostMapping("selectIssuePartyId")
    public String selectIssuePartyId(@RequestBody String orgCode){
        return  salerIdenInfo002Service.selectIssuePartyId(orgCode);
    }

    /**
     * 总部或分支机构编码联想框
     */
    @PostMapping("getSalerOrgCode")
    public List<SuggestDto> getSalerOrgCode(@RequestBody Map<String, Object> map) {
        return salerIdenInfo001Service.getSalerOrgCode(map);
    }
}
