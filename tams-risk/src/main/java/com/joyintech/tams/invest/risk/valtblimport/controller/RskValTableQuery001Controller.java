package com.joyintech.tams.invest.risk.valtblimport.controller;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblAssetSubDto;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblAssetSumDto;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblFileInfoDto;
import com.joyintech.tams.invest.risk.valtblimport.service.RskValTableQueryService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *   估值表查询
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月27日
 * @see RskValTableQuery001Controller
 * @since 1.0
 */
@Controller
@PageId("RskValTableQuery001")
@RequestMapping("/invest/risk/valtblimport/RskValTableQuery001")
public class RskValTableQuery001Controller extends BaseController {

    @Autowired
    private RskValTableQueryService rskValTableQueryService;
    @Override
    public String init(Model model){
        this.setSelectOptionToModel(model, DictCode.资产_负债_一级类别);
        this.setSelectOptionToModel(model, DictCode.资产_负债_二级类别);
        this.setSelectOptionToModel(model, DictCode.异常标识);
        return super.init(model);
    }

    @ResponseBody
    @RequestMapping("/getFileName")
    public List<SelectOptionDto> getFileName(){
        String param = super.getRequest().getParameter("search");

        return rskValTableQueryService.getFileName(param);
    }
    @ResponseBody
    @RequestMapping("/getFileHeader")
    public List<SelectOptionDto> getFileHeader(){
        String param = super.getRequest().getParameter("search");
        List<SelectOptionDto> temp =rskValTableQueryService.getFileHeader(param);
        return temp;
    }
    @ResponseBody
    @RequestMapping("/getSubCode")
    public List<SelectOptionDto> getSubCode(){
        String param = super.getRequest().getParameter("search");
        List<SelectOptionDto> temp =rskValTableQueryService.getSubCode(param);
        return temp;
    }


    /**
     * 初始化查询
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<RskValtblFileInfoDto> selectAll()
             {
        Page<RskValtblFileInfoDto> page = rskValTableQueryService.selectForPage(
                RequestUtil.getParameters(getRequest()));
        return page;
    }

    /**
     * 详情查看初始化
     * @param model
     * @return
     */
    @RequestMapping(value = "/detailedInit")
    public String detaile(Model model) {
        String paramJsonStr = this.getParam("paramJsonStr");
        model.addAttribute("paramJsonStr", paramJsonStr);
        return "invest/risk/valtblimport/RskValTableQueryDetail";
    }

    /**
     * 查看估值表明细
     * <功能详细描述>
     * @param dto
     * @return
     * @throws ParseException
     * @see
     * @since 1.0
     */
    @RequestMapping(value = "/selectRskValTableDetaile")
    @ResponseBody
    public List<RskValtblAssetSubDto> selectRskValTableDetaile(@RequestBody RskValtblAssetSumDto dto)
            throws ParseException {
        Map<String,Object> param = new HashedMap();
        param.put("ptlId",dto.getPtlId());
        param.put("valDate", DateUtil.format(dto.getValDate()));
        return  rskValTableQueryService.selectRskValTableDetaile(param);
    }

    /**
     * 导出
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @RequestMapping(ACTION_EXPORT)
    public ResponseEntity<byte[]> export()
             {
        Map<String, Object> param = RequestUtil.getParameters(getRequest());
        return rskValTableQueryService.export(this.getPageId(),param);
    }
}
