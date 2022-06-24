package com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.*;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.system.constant.SystemGlobalConstant;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.dto.ChbMstBondCreditRatingDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.service.ChbMstBondCreditRateService;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wubingbing
 * @date 2022-04-01 15:04
 */
@Controller
@PageId("ChbMstBondCreditRating001")
@RequestMapping("/chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating001")
public class ChbMstBondCreditRating001Controller extends BaseController {
    @Autowired
    private ChbMstBondCreditRateService mstBondCreditRateService;
    @Override
    public String init(Model model) {
        //评级对象
        this.setSelectOptionToModel(model, DictCode.评级类型);
        //评级类型
        this.setSelectOptionToModel(model, DictCode.长期短期);
        this.setSelectOptionToModel(model, DictCode.维护数据状态);

        return super.init(model);
    }
    /**
     * 债券简称的下拉框
     *
     * @param param 参数
     * @return 查询结果集
     * @throws Exception 异常
     */
    @ResponseBody
    @RequestMapping("getOName")
    public List<SuggestDto> getOName(String param)
            throws Exception {
        return mstBondCreditRateService.getFinProdAbbr(param);
    }



    /**
     * 资产代码下拉框
     * @return 产品名称列表
     */
    @RequestMapping("/getMutliCode")
    @ResponseBody
    public List<SelectOptionDto> getMutliCode() {
        return mstBondCreditRateService.getMutliCode(RequestUtil.getPageParam());
    }

    /**
     * 分页查询
     *
     * @return Page<MstMarketManagementDto>泛型的page实体类
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<ChbMstBondCreditRatingDto> selectAllMstMarketManagement(){

        Page<ChbMstBondCreditRatingDto> page = mstBondCreditRateService.selectForPage(RequestUtil.getPageParam());

        return page;

    }

    /**
     * 删除按钮点击时的check
     *
     * @param dto dto
     * @param result BindingResult
     * @return check结果
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody @Validated({ValidateGroupDelete.class}) ChbMstBondCreditRatingDto dto,
                            BindingResult result) {
        return commonCheck(result, dto.getOperateType());
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
            ValidateGroupDelete.class}) ChbMstBondCreditRatingDto dto, BindingResult br)
            throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        ProcessParameter<ChbMstBondCreditRatingDto> processParameter = new ProcessParameter<ChbMstBondCreditRatingDto>();
        WorkFlowParameter<ChbMstBondCreditRatingDto> params = new WorkFlowParameter<ChbMstBondCreditRatingDto>();
        params.setPageId("ChbMstBondCreditRating002");
        params.setElementId(dto.getElementId());
        params.setBusinessCode(item -> item.getoCode()+item.getGradeOrgId()+item.getGradeDateStr()+item.getoType());
        params.setBusinessType(DictCodeList.共通业务类型_债券评级信息.getValue());
        // 设置流程业务说明, entity即传入的dto
        params.setComment(entity -> this.getMessage("ChbMstBondCreditRating001.oCode") + "("
                + entity.getoCode() + ")"
                +this.getMessage("ChbMstBondCreditRating001.oType")
                +"("+entity.getoType()+")");
        processParameter.setWorkFlowParameter(params);
        dto.getSelectedItem().forEach(e->{
            e.setCreateUser(CommonUtil.getUserName());
            e.setCreateTime(DateUtil.now());
            e.setUpdateUser(CommonUtil.getUserName());
            e.setUpdateTime(DateUtil.now());
        });
        String msg =mstBondCreditRateService.start(processParameter,dto.getSelectedItem().toArray(new ChbMstBondCreditRatingDto[dto.getSelectedItem().size()]));
        return new AjaxResult(msg);

    }
    /**
     * 导入（必填项校验）
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     * @throws Exception 异常
     */
    @ResponseBody
    @RequestMapping("importFile")
    public List<ExcelValidDto> importData(String fileName)
            throws Exception{
        return this.mstBondCreditRateService.importData(fileName);
    }

    /**
     * 导入（数据保存）
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("importFileSave")
    public List<ExcelValidDto> importFileSave(String fileName,String tableName)
            throws Exception {

        return mstBondCreditRateService.importFileSave(fileName,tableName);
    }
    /**
     * 导出信息excel
     *
     * @return excel
     * @throws Exception 异常
     * @see
     * @since 1.0
     */

    @RequestMapping("export")
    public ResponseEntity<byte[]> export()
            throws Exception {
        /** modify by yanya on 20210209 增加按照选择数据进行导出 start**/
        String datas = this.getParam("datas");
        Map<String, Object> params= RequestUtil.getPageParam();
        List<ChbMstBondCreditRatingDto> list = null;
        if (StringUtil.isNotEmpty(datas)) {
            list = JsonUtils.json2List(datas, ChbMstBondCreditRatingDto.class);
        }
        else {
            list = mstBondCreditRateService.selectAll(params);
        }
//        list.forEach(item->{
////            item.setGradeDateStr(DateUtil.format(item.getGradeDate(),"yyyy-MM-dd"));
//            item.setUpdateTimeStr(DateUtil.format(item.getUpdateTime(),"yyyy-MM-dd"));
//        });
        File outFile = mstBondCreditRateService.makeFile(
                "/com/joyintech/tams/svreport/chb/businessdatamaintain/chbmstbondcreditrating/conf/export_conf.xml",
                list);
        return ExportUtil.export(
                this.getMessage(this.getPageId()) + SystemGlobalConstant.OPEN_PARENTHESIS
                        + DateUtil.format(new Date())
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
    @RequestMapping(value = "checkConfirmMsgForImportData", method = RequestMethod.POST)
    public AjaxResult checkConfirmMsgForImportData(String fileName,String tableName) throws Exception  {
        return mstBondCreditRateService.checkConfirmMsgForImportData(fileName,tableName);
    }
}
