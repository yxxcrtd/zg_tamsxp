package com.joyintech.tams.report.submitmaintenance.salerideninfo.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.dto.SvrAjaxResult;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.dao.ChbSalerIdenInfoMapper;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.dto.SalerIdenInfoDto;
import com.joyintech.tams.systemtools.dto.CommonAjaxResult;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.joyintech.framework.grid.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalerIdenInfoService extends CommonExportService<SalerIdenInfoDto> {

    @Autowired
    private ChbSalerIdenInfoMapper chbSalerIdenInfoMapper;
    /**
     * 分页查询列表
     *
     * @return Page
     */
    public Page<SalerIdenInfoDto> searchForPage(Map<String, Object> param) {
        return restTemplate.postForPage(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/findPage",param,
                SalerIdenInfoDto.class);
    }

    /**
     * 双击查看或编辑info
     * selectDetail
     */
    public SalerIdenInfoDto selectDetail(String salerInsNo,String erroOrInfo,String workflowId) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("salerInsNo",salerInsNo);
        param.put("erroOrInfo",erroOrInfo);
        param.put("workflowId",workflowId);
        return restTemplate.postForObject(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/selectDetail", param,
                SalerIdenInfoDto.class);
    }

    /**
     * 提交保存
     * @param dto
     * @return
     */
    public AjaxResult saveAndSubmit(SalerIdenInfoDto dto){
        if(StringUtil.isEmpty(dto.getOrgCode())){
            dto.setOrgCode(CommonUtil.getOrgCode());
        }
        if(StringUtil.isEmpty(dto.getCreateDept())){
            dto.setCreateDept(CommonUtil.getDeptCode());
        }
        return restTemplate.postForObject(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/saveAndSubmit", dto,
                SvrAjaxResult.class);
    }

    /**
     * 修改保存
     * @param dto
     * @return
     */
    public AjaxResult update(SalerIdenInfoDto dto){
        if(StringUtil.isEmpty(dto.getOrgCode())){
            dto.setOrgCode(CommonUtil.getOrgCode());
        }
        if(StringUtil.isEmpty(dto.getCreateDept())){
            dto.setCreateDept(CommonUtil.getDeptCode());
        }
        return restTemplate.postForObject(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/update", dto,
                SvrAjaxResult.class);
    }

    /**
     * 导出
     * @param params
     * @return
     * @throws Exception
     */
    public List<SalerIdenInfoDto> queryAllList(Map<String, Object> params) throws Exception{
        return restTemplate.postForList(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/queryAllList",
                params, SalerIdenInfoDto.class);
    }

    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    public AjaxResult checkConfirmMsgForImportData(String fileName)  {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("dataList", ExcelUtil.getDataList(fileName));
        param.put("orgCode", CommonUtil.getOrgCode());
        param.put("deptCode", CommonUtil.getDeptCode());
        param.put("fileName", fileName);
        return restTemplate.postForObject(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/checkConfirmMsgForImportData",
                param,
                CommonAjaxResult.class);
    }

    /**
     * 导入数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    public List<ExcelValidDto> importData(String fileName) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("dataList", ExcelUtil.getDataList(fileName));
        param.put("tempList", ExcelUtil.getTempList(fileName));
        param.put("userName", CommonUtil.getUserName());
        param.put("orgCode", CommonUtil.getOrgCode());
        param.put("deptCode", CommonUtil.getDeptCode());
        return restTemplate.postForList(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/importData",
                param,
                ExcelValidDto.class);
    }

    /**
     * 导入销售人员登记编码之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    public AjaxResult checkConfirmMsgForImportCodeData(String fileName)  {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("dataList", ExcelUtil.getDataList(fileName));
        param.put("orgCode", CommonUtil.getOrgCode());
        param.put("deptCode", CommonUtil.getDeptCode());
        param.put("fileName", fileName);
        return restTemplate.postForObject(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/checkConfirmMsgForImportCodeData",
                param,
                CommonAjaxResult.class);
    }

    /**
     * 导入销售人员登记编码
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    public List<ExcelValidDto> importCode(String fileName) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("dataList", ExcelUtil.getDataList(fileName));
        param.put("tempList", ExcelUtil.getTempList(fileName));
        param.put("userName", CommonUtil.getUserName());
        param.put("orgCode", CommonUtil.getOrgCode());
        param.put("deptCode", CommonUtil.getDeptCode());
        return restTemplate.postForList(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/importCode",
                param,
                ExcelValidDto.class);
    }

    /**
     * 逻辑删除
     * <功能详细描述>
     * @param dto 待校验数据
     * @return jiaoyanjieguo
     * @see
     * @since 1.0
     */
    public AjaxResult deleteByPrimaryKey(SalerIdenInfoDto dto){
        return restTemplate.postForObject(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/deleteByPrimaryKey", dto,
                CommonAjaxResult.class);
    }

    /**
     * 查询发行机构代码
     * @param orgCode
     * @return
     */
    public String selectIssuePartyId(String orgCode) {
         return  restTemplate.postForObject(
                SvrServiceApplication.CHB_SERVICE_NAME + "/SalerIdenInfo/selectIssuePartyId",orgCode,
                String.class);
    }

    /**
     * 理财销售人员姓名联想框
     */
    public List<SuggestDto> getSalerName(Map<String, Object> map) {
        return restTemplate.postForList(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/getSalerName",
                map, SuggestDto.class);
    }

    /**
     * 所属总部或分支机构下拉框（新建）
     */
    public List<SelectOptionDto> selectSalerOrgCode(Map<String, Object> map) {
        List<SelectOptionDto> dtoList = new ArrayList<SelectOptionDto>();
        dtoList = chbSalerIdenInfoMapper.selectSalerOrgCode(map);
        return dtoList;
    }

    /**
     * 总部或分支机构编码联想框
     */
    public List<SuggestDto> getSalerOrgCode(Map<String, Object> map) {
        return restTemplate.postForList(
                SvrServiceApplication.CHB3_SERVICE_NAME + "/SalerIdenInfo/getSalerOrgCode",
                map, SuggestDto.class);
    }
}
