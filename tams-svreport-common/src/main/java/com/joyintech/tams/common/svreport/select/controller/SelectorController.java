package com.joyintech.tams.common.svreport.select.controller;


import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.tams.common.base.entity.MstCustomerInfoAdd;
import com.joyintech.tams.common.svreport.select.service.SelectorService;
import com.joyintech.tams.constant.DictCodeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * MST_CUSTOMER_INFO
 * @author yxj
 * @date 2022/1/5
 */
@Controller
@RequestMapping("/common/select")
public class SelectorController {

    @Autowired
    private SelectorService selectorService;

    /**
     * 发行人
     * @return
     */
    @ResponseBody
    @RequestMapping("/getIssuerId")
    public Page<SelectOptionDto> getIssuerId(){
        Map<String,Object> map = RequestUtil.getPageParam();
        map.put("param", map.get("search"));
        map.put("IS_ISSUER", DictCodeList.是否_是.getValue());
        Page<SelectOptionDto> temp = selectorService.getCustomerId(map);
        return temp;
    }

    /**
     * 融资人
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFinancierId")
    public Page<SelectOptionDto> getFinancierId() {
        Map<String,Object> map = RequestUtil.getPageParam();
        map.put("param", map.get("search"));
        map.put("IS_FINANCIER", DictCodeList.是否_是.getValue());
        return selectorService.getCustomerId(map);
    }

    /**
     * 管理人
     * @return
     */
    @ResponseBody
    @RequestMapping("/getManagerId")
    public Page<SelectOptionDto> getManagerId() {
        Map<String,Object> map = RequestUtil.getPageParam();
        map.put("param", map.get("search"));
        //map.put("IS_ASSTE_MANAGER", DictCodeList.是否_是.getValue());
        return selectorService.getCustomerId(map);
    }

    /**
     * 托管人
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTrusterId")
    public Page<SelectOptionDto> getTrusterId() {
        Map<String,Object> map = RequestUtil.getPageParam();
        map.put("param", map.get("search"));
        //map.put("IS_TRUSTER", DictCodeList.是否_是.getValue());
        return selectorService.getCustomerId(map);
    }


    /**
     * 托管人
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPbcCapAssetInfo")
    public List<SelectOptionDto> getPbcCapAssetInfo() {
        Map<String,Object> map = RequestUtil.getPageParam();
        map.put("param", map.get("search"));
        //map.put("IS_TRUSTER", DictCodeList.是否_是.getValue());
        return null;
    }

    /**
     * 担保人
     * @return
     */
    @ResponseBody
    @RequestMapping("/getGuaranteeId")
    public Page<SelectOptionDto> getGuaranteeId() {
        Map<String,Object> map = RequestUtil.getPageParam();
        map.put("param", map.get("search"));
        map.put("IS_GUARANTEE", DictCodeList.是否_是.getValue());
        return selectorService.getCustomerId(map);
    }

    /**
     * 发行人 的 机构规模和行业
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/getIssuerInfo")
    public MstCustomerInfoAdd getSvrBussFinBondRepInfo(@RequestBody Map<String, Object> map){
        return selectorService.getCustomerInfo(map);
    }

    /**
     *  融资人 的 机构规模和行业
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFinancierInfo")
    public MstCustomerInfoAdd getFinancierInfo(@RequestBody Map<String, Object> map){
        return selectorService.getCustomerInfo(map);
    }

    /**
     * 管理人 的 机构规模和行业
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/getManagerInfo")
    public MstCustomerInfoAdd getManagerInfo (@RequestBody Map<String, Object> map){
        return selectorService.getCustomerInfo(map);
    }

}
