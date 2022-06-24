package com.joyintech.service.chb.basicdata.perprodeclareinfo.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.service.chb.basicdata.perprodeclareinfo.service.SvrPerProDeclareInfoMService;
import com.joyintech.tams.basicdata.perprodeclareinfo.dto.SvrPerProDeclareInfo001Dto;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 产品申报信息登记维护
 * @author yxj
 * @date 2022/03/07
 */
@RestController
@RequestMapping("/SvrPerProDeclareInfo")
public class SvrPerProDeclareInfoController{
    //
    @Autowired
    private SvrPerProDeclareInfoMService  svrPerProDeclareInfoMService;

    /**
     * 分页查询列表
     *
     * @return Page
     */
    @PostMapping("/selectForPage/{page}/{length}")
    public Page<SvrPerProDeclareInfo001Dto> selectForPage(@RequestBody Map<String, Object> pageParam, @PathVariable("page") int page, @PathVariable("length") int length) {
        Page<SvrPerProDeclareInfo001Dto> p =  svrPerProDeclareInfoMService.selectForPage(page,length,pageParam);
        return p;
    }

    /**
     * 获取详细信息
     * @param param
     * @return
     */
    @PostMapping("selectDetail")
    public SvrPerProDeclareInfo001Dto selectDetail(@RequestBody Map<String, Object> param){
        return svrPerProDeclareInfoMService.selectDetail(param);
    }

    /**
     * 联想下拉框
     * @param param
     * @return
     */
    @PostMapping("queryProdInfo")
    public List<SuggestDto> queryProdInfo(@RequestBody Map<String, Object> param){
        List<SuggestDto> list = svrPerProDeclareInfoMService.queryProdInfo(param);
        return list;
    }

    /**
     * 获取所有
     * @param param
     * @return
     */
    @PostMapping("queryAllList")
    public List<SvrPerProDeclareInfo001Dto> queryAllList(@RequestBody Map<String, Object> param){
        List<SvrPerProDeclareInfo001Dto> list = svrPerProDeclareInfoMService.queryAllList(param);
        return list;
    }

    /**
     * 保存
     * @param dto
     */
    @PostMapping("save")
    public void Save(@RequestBody SvrPerProDeclareInfo001Dto dto) {
        svrPerProDeclareInfoMService.save(dto);
    }

    /**
     * 修改
     * @param dto
     */
    @PostMapping("update")
    public void update(@RequestBody SvrPerProDeclareInfo001Dto dto) {
        svrPerProDeclareInfoMService.update(dto);
    }

    /**
     * 删除
     * @param dto
     */
    @PostMapping("delete")
    public void deleteByPrimaryKey(@RequestBody SvrPerProDeclareInfo001Dto dto){
        svrPerProDeclareInfoMService.deleteByPrimaryKey(dto);
    }

    /**
     * 批量删除
     * @param param
     */
    @PostMapping("deleteList")
    public void deleteList(@RequestBody Map<String, Object> param){
        svrPerProDeclareInfoMService.deleteList(param);
    }

    /**
     * 导入数据前检查
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    @PostMapping("checkConfirmMsgForImportData")
    public AjaxResult checkConfirmMsgForImportData(@RequestBody Map<String, Object> param)  {
        List<List<String>> dataList = (List<List<String>>) param.get("dataList");
        String orgCode=param.get("orgCode")==null?null:param.get("orgCode").toString();
        String deptCode=param.get("deptCode")==null?null:param.get("deptCode").toString();
        return svrPerProDeclareInfoMService.checkConfirmMsgForImportData(dataList);
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
//        String orgCode=param.get("orgCode")==null?null:param.get("orgCode").toString();
        String deptCode=param.get("deptCode")==null?null:param.get("deptCode").toString();
        return svrPerProDeclareInfoMService.importData(dataList,tempList,userName,deptCode);
    }
}
