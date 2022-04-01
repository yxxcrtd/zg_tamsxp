package com.joyintech.tams.svreport.chb.assetinfodetail.asset.project.controller;

import com.joyintech.framework.annotation.SubPageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.chb.assetinfodetail.asset.project.impl.SvrBussRepEquityInfoImplService;
import com.joyintech.tams.svreport.chb.assetinfodetail.asset.project.impl.SvrBussRepOtherInvestTargetInfoImplService;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset.SvrBussRepEquityInfoDto;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset.SvrBussRepOtherInvestTargetInfoDto;
import com.joyintech.tams.svreport.chb.assetinfodetail.service.AssetInfoDetailMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 其他类资产--中债报送
 * 〈功能详细描述〉
 * @author
 * @version 1.0 2021年01月14日
 * @see SvrBussRepOtherInvestTargetInfoController
 * @since 1.0
 */
@Controller
@RequestMapping("/chb/assetinfodetail/asset/project/SvrBussRepOtherInvestTargetInfo")
@SubPageId("SvrBussRepOtherInvestTargetInfo")
public class SvrBussRepOtherInvestTargetInfoController extends BaseController{

    @Autowired
    private SvrBussRepOtherInvestTargetInfoImplService bussAssetInfoService;

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
        setSelectOptionToModel(model,   new DictCode[] {DictCode.行内资产类别,DictCode.所属国家或地区,DictCode.是否_中债,
                DictCode.发行机构类型_按规模划分,DictCode.发行机构类型_按经济类型划分,DictCode.发行机构类型_按技术领域划分,
                DictCode.机构所属行业一级分类,DictCode.机构所属行业二级分类,DictCode.重点监控行业和领域类别,DictCode.资产评级,
                DictCode.担保方式,DictCode.抵质押物类型,DictCode.担保性质,DictCode.担保人与融资人关系
                ,DictCode.人行资产一级分类,DictCode.人行资产二级分类,DictCode.人行资产三级分类, DictCode.结构化业务类型,DictCode.结构化层级}
         );

        //2、当入参assetType2资产/负债二级类别 为【资产_负债类别_各类资产的其他项】进行以下处理
        if(assetInfoDetailMService.getDataListLink().get(DictCodeListExt2.资产_负债类别_各类资产的其他项.getValue()).contains(assetType)){
              //调用service
            bussAssetInfoService.setModle(uuid, dataStatus, model,workFlowId,entityName);
        }else {
            SvrBussRepOtherInvestTargetInfoDto dto = new SvrBussRepOtherInvestTargetInfoDto();
            dto.setPppflag(DictCodeList.是否_中债_否.getValue());
            dto.setBondtostockFlag(DictCodeList.是否_中债_否.getValue());
            dto.setPrivateprdFlag(DictCodeList.是否_中债_否.getValue());
            model.addAttribute(GlobalConstant.ENTITY, dto);
        }
        model.addAttribute("entityName",this.getParam("entityName"));

        //3、返回 子组件对应的页面
        return "report/chb/assetinfodetail/asset/project/SvrBussRepOtherInvestTargetInfo";
    }

}
