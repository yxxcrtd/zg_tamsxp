package com.joyintech.tams.svreport.chb.assetinfodetail.asset.project.controller;

import com.joyintech.framework.annotation.SubPageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.chb.assetinfodetail.asset.project.impl.SvrBussRepAssetCreditNonstdInfoImplService;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset.SvrBussRepAssetCreditNonstdInfoDto;
import com.joyintech.tams.svreport.chb.assetinfodetail.service.AssetInfoDetailMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 非标准化债权类资产/新增可投资资产--中债报送
 * 〈功能详细描述〉
 * @author
 * @version 1.0 2021年01月14日
 * @see SvrBussRepAssetCreditNonstdInfoController
 * @since 1.0
 */
@Controller
@RequestMapping("/chb/assetinfodetail/asset/project/SvrBussRepAssetCreditNonstdInfo")
@SubPageId("SvrBussRepAssetCreditNonstdInfo")
public class SvrBussRepAssetCreditNonstdInfoController extends BaseController{

    @Autowired
    private SvrBussRepAssetCreditNonstdInfoImplService bussAssetInfoService;

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
        setSelectOptionToModel(model,   new DictCode[] {DictCode.基准利率,DictCode.是否_中债,DictCode.资产_负债二级类别,DictCode.交易流通场所,DictCode.收受益权类型,
                DictCode.重点监控行业和领域类别,DictCode.担保方式,DictCode.资产评级,DictCode.计息基础_中债,
                DictCode.发行机构类型_按技术领域划分,DictCode.发行机构类型_按规模划分,DictCode.发行机构类型_按经济类型划分
                ,DictCode.机构所属行业一级分类,DictCode.机构所属行业二级分类,DictCode.行政区划_市,DictCode.资产评级,DictCode.人行机构规模,DictCode.受让形式
                ,DictCode.人行资产一级分类,DictCode.人行资产二级分类,DictCode.人行资产三级分类}
         );
        //中债新增数据字典
        setSelectOptionToModel(model, DictCode.计息类型);
        setSelectOptionToModel(model, DictCode.利息分布方式);
        setSelectOptionToModel(model, DictCode.结构档次);
        setSelectOptionToModel(model, DictCode.还本方式);
        setSelectOptionToModel(model, DictCode.分期还本条款标识);
        setSelectOptionToModel(model, DictCode.抵质押物类型);
        setSelectOptionToModel(model, DictCode.担保人与融资人关系);
        setSelectOptionToModel(model, DictCode.含权类型);
        setSelectOptionToModel(model, DictCode.担保性质);
        setSelectOptionToModel(model, DictCode.资产行权方式);
        setSelectOptionToModel(model, DictCode.永续条款类型);
        setSelectOptionToModel(model, DictCode.利息递延条款类型);
        setSelectOptionToModel(model, DictCode.是或否);
        setSelectOptionToModel(model, DictCode.结构化业务类型);
        setSelectOptionToModel(model, DictCode.结构化层级);

        //2、当入参assetType2资产/负债二级类别 为【非标准化债权类资产/新增可投资资产】进行以下处理
        if(assetInfoDetailMService.getDataListLink().get(DictCodeListExt2.资产_负债类别_非标准化债权类资产_信贷资产流转项目.getValue()).contains(assetType)){
              //调用service
            bussAssetInfoService.setModle(uuid, dataStatus, model,workFlowId,entityName);
        }else {
            SvrBussRepAssetCreditNonstdInfoDto dto = new SvrBussRepAssetCreditNonstdInfoDto();
            dto.setPppflag(DictCodeList.是否_中债_否.getValue());
            dto.setBondtostockFlag(DictCodeList.是否_中债_否.getValue());
            dto.setRepPrivateprdFlag(DictCodeList.是否_中债_否.getValue());
            model.addAttribute(GlobalConstant.ENTITY, dto);
        }
        model.addAttribute("entityName",this.getParam("entityName"));

        //3、返回 子组件对应的页面
        return "report/chb/assetinfodetail/asset/project/SvrBussRepAssetCreditNonstdInfo";
    }

}
