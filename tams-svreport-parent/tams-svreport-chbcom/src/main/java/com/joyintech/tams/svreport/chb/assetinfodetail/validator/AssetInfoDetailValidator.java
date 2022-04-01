/*
 * 文件名：FinDepositTargetValidator.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年10月30日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.svreport.chb.assetinfodetail.validator;

import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.svreport.chb.assetinfodetail.dao.AssetInfoDetailMapper;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.AssetInfoDetailDto;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset.SvrBussRepQDIIStructureInfoDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *  存款类资产--业务验证
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年10月30日
 * @see AssetInfoDetailValidator
 * @since 1.0
 */
@Component
public class AssetInfoDetailValidator implements BeanValidator{


    @Autowired
    private DataCheckCommonUtil chbDataCheckCommonUtil;

    @Autowired
    private AssetInfoDetailMapper assetInfoDetailMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ValidMessage> valid(Object value) {
        AssetInfoDetailDto dto = (AssetInfoDetailDto)value;
        List<ValidMessage> resultList = new ArrayList<ValidMessage>();

//        if(StringUtil.isEmpty(dto.getAssetCode())){
//            resultList.add(new ValidMessage("",
//                    getMessage("COMMON_E_0057", getMessage("资产编号"))));
//        }
//        if(StringUtil.isEmpty(dto.getAssetName())){
//            resultList.add(new ValidMessage("",
//                    getMessage("COMMON_E_0057", getMessage("资产名称"))));
//        }
//        if(!StringUtil.isNotEmpty(dto.getVdate())){
//            resultList.add(new ValidMessage("",
//                    getMessage("COMMON_E_0057", getMessage("起息日"))));
//        }
//        if(!StringUtil.isNotEmpty(dto.getMdate())){
//            resultList.add(new ValidMessage("",
//                    getMessage("COMMON_E_0057", getMessage("到期日"))));
//        }
//        if(StringUtil.isEmpty(dto.getOrgAssetType1())){
//            resultList.add(new ValidMessage("",
//                    getMessage("COMMON_E_0057", getMessage("资产一级分类"))));
//        }
//
//        if(StringUtil.isEmpty(dto.getAssetType2())){
//            resultList.add(new ValidMessage("",
//                    getMessage("COMMON_E_0057", getMessage("资产负债二级类别"))));
//        }
//
//        if(StringUtil.isEmpty(dto.getCcy())){
//            resultList.add(new ValidMessage("",
//                    getMessage("COMMON_E_0057", getMessage("币种"))));
//        }
//        if(StringUtil.isEmpty(dto.getTradeMarket())){
//            resultList.add(new ValidMessage("",
//                    getMessage("COMMON_E_0057", getMessage("市场机构代码"))));
//        }
        //如果资产没有资产编号.默认为新增.检查是否存在对应资产
        if(StringUtil.isEmpty(dto.getAssetUuid())){
            AssetInfoDetailDto resultDto = assetInfoDetailMapper.queryAssetInfo(dto.getAssetCode());
            if(null!=resultDto){
                resultList.add(new ValidMessage("assetCode","资产编码已存在"));
            }
        }
        dto.setIssuePartyId(CommonUtil.selectBankCode());
        // add by zhangliang 20210126 中债check调用 start
        resultList = chbDataCheckCommonUtil.chbCheckCommon(resultList, dto, null, dto.getAssetType2(), ValidMessage.class);
        // add by zhangliang 20210126 中债check调用 end
        if(StringUtil.equals(dto.getAssetType2(), "1503")){
            //“固定收益部分所占比例”与“衍生金融工具所占比例”之和需小于等于1
            SvrBussRepQDIIStructureInfoDto svrBussRepQDIIStructureInfoDto = dto.getSvrBussRepQDIIStructureInfoDto();
            if(StringUtil.isNotEmpty(svrBussRepQDIIStructureInfoDto.getIncomeRatio())  &&
                    StringUtil.isNotEmpty(svrBussRepQDIIStructureInfoDto.getDerInstrRatio())){
                if(svrBussRepQDIIStructureInfoDto.getIncomeRatio().add(svrBussRepQDIIStructureInfoDto.getDerInstrRatio()).compareTo(new BigDecimal(100))>0){
                    resultList.add(new ValidMessage("svrBussRepQDIIStructureInfoDto.incomeRatio","以百分数形式表示的固定收益部分所占比例与衍生金融工具所占比例之和需小于等于100",""));

                }
            }
        }
        //modify by tuqirong 2022-01-10 start 错误信息去重处理
        Map<String, Object> errMap = new HashMap<>();
        List<ValidMessage> result = new ArrayList<>();
        resultList.forEach(validMessage -> {
            if (!errMap.containsKey(validMessage.getMessage())) {
                result.add(validMessage);
                errMap.put(validMessage.getMessage(),validMessage.getMessage());
            }
        });
        resultList.clear();
        resultList.addAll(result);
        //modify by tuqirong 2022-01-10 end 错误信息去重处理
        return resultList;
    }

}
