package com.joyintech.tams.svreport.chb.assetinfodetail.asset.plan.controller;

import com.joyintech.framework.annotation.SubPageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.chb.assetinfodetail.asset.plan.impl.SvrBussRepAssetMngProdInfoImplService;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset.SvrBussRepAssetMngProdInfoDto;
import com.joyintech.tams.svreport.chb.assetinfodetail.service.AssetInfoDetailMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 资产管理产品--中债报送
 * 〈功能详细描述〉
 * @author
 * @version 1.0 2021年01月14日
 * @see SvrBussRepAssetMngProdInfoController
 * @since 1.0
 */
@Controller
@RequestMapping("/chb/assetinfodetail/asset/plan/SvrBussRepAssetMngProdInfo")
@SubPageId("SvrBussRepAssetMngProdInfo")
public class SvrBussRepAssetMngProdInfoController extends BaseController{

    @Autowired
    private SvrBussRepAssetMngProdInfoImplService bussAssetInfoService;

    @Autowired
    private AssetInfoDetailMService assetInfoDetailMService;

    @Override
    public String init(Model model) {
        //获取请求参数
        HttpServletRequest  requestPargram=this.getRequest();
        //UUID
        String    uuid=requestPargram.getParameter("assetUuid");
        //模式  ※err表记录：01； 资产要素表信息表：02；
        String    dataStatus=requestPargram.getParameter("dataStatus");
        //资产二级类别
        String   assetType=requestPargram.getParameter("assetType");

        //工作流代码
        String workFlowId = this.getParam("workflowId");

        String entityName = this.getParam("entityName");

        //1.赋值界面数据字典
        setSelectOptionToModel(model,   new DictCode[] {DictCode.是否_中债, DictCode.资金实际投向,
                DictCode.机构所属行业一级分类,DictCode.机构所属行业二级分类,DictCode.资管计划属性,
                DictCode.是否_中债,
                DictCode.购买结构,DictCode.管理方式,DictCode.资产_负债二级类别
                ,DictCode.人行资产一级分类,DictCode.人行资产二级分类,DictCode.人行资产三级分类}
         );

        //2、当入参assetType2资产/负债二级类别 为【资产管理产品】进行以下处理
        if(assetInfoDetailMService.getDataListLink().get(DictCodeListExt2.资产_负债类别_资产管理产品.getValue()).contains(assetType)){
              //调用service
            bussAssetInfoService.setModle(uuid, dataStatus, model,workFlowId,entityName);
        }else {
            SvrBussRepAssetMngProdInfoDto dto = new SvrBussRepAssetMngProdInfoDto();
            dto.setPppflag(DictCodeList.是否_中债_否.getValue());
            dto.setBondtostockFlag(DictCodeList.是否_中债_否.getValue());
            model.addAttribute(GlobalConstant.ENTITY, dto);
        }
        model.addAttribute("entityName",this.getParam("entityName"));

        //3、返回 子组件对应的页面
        return "report/chb/assetinfodetail/asset/plan/SvrBussRepAssetMngProdInfo";
    }

}
