/*
 * 文件名：MstBankTranFeeManageValidator.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2019年3月25日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.basicdata.mstbanktranfeemanage.valid;

import com.joyintech.framework.constant.DictCodeList;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.component.business.core.dto.WflValidInputParameter;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.invest.basicdata.mstbanktranfeemanage.dto.MstBankTranFeeManageDto;
import com.joyintech.tams.invest.basicdata.mstbanktranfeemanage.service.MstBankTranFeeManageService;
import com.joyintech.tams.invest.common.constant.BussinessDictCode;
import com.joyintech.tams.invest.common.constant.BussinessDictCodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author wangzehui
 * @version 1.0 2019年3月25日
 * @see MstBankTranFeeManageValidator
 * @since 1.0
 */
@Component
public class MstBankTranFeeManageValidator implements BeanValidator {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(MstBankTranFeeManageValidator.class);

    /**
     * mstBankTranFeeManageService
     */
    @Autowired
    private MstBankTranFeeManageService mstBankTranFeeManageService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ValidMessage> valid(Object value) {
        MstBankTranFeeManageDto dto = (MstBankTranFeeManageDto) value;
        List<ValidMessage> resultList = new ArrayList<ValidMessage>();
        if (StringUtil.equals(dto.getOperateType(), "delete")) {
            // 删除check
            List<MstBankTranFeeManageDto> list = dto.getSelectedItem();
            for (MstBankTranFeeManageDto d : list) {
                // 判断选择的数据是否存在于数据库中
                if (!mstBankTranFeeManageService.isExit(d.getBatchSeq())) {
                    resultList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0022")));
                }
            }
        } else {
            // 当交易品种为债券时，交易市场必填
            if(StringUtil.equals(dto.getTradeType(),BussinessDictCodeList.平安资产类型_债券.getValue())
                    && StringUtil.isEmpty(dto.getTradeMarketMode())){
                resultList.add(new ValidMessage(
                        "tradeMarketMode",this.getMessage("FW_E_0006", getMessage("MstBankTranFeeManage001.tradeMarketMode"))));
            }
            // 当交易品种为回购时，期限品种必填
            if ((StringUtil.equals(dto.getTradeType(), BussinessDictCodeList.平安资产类型_回购.getValue())
            || StringUtil.equals(dto.getTradeType(), BussinessDictCodeList.平安资产类型_买断式回购.getValue()))
                    && StringUtil.isEmpty(dto.getTermType())) {
                resultList.add(new ValidMessage(
                        "termType",this.getMessage("FW_E_0006", getMessage("MstBankTranFeeManage002.termType"))));
            }
            // 当计费类型是固定金额时，费用值必填
            if (StringUtil.equals(dto.getChargeType(), BussinessDictCodeList.计费类型_固定金额.getValue())
                    && dto.getChargeValue() == null) {
                resultList.add(new ValidMessage("chargeValue",this.getMessage("FW_E_0006",
                        getMessage("MstBankTranFeeManage002.chargeValue"))));
            }
            // 当计费类型是固定费率时，费率值必填
            if (StringUtil.equals(dto.getChargeType(), BussinessDictCodeList.计费类型_固定费率.getValue())
                    && dto.getChargeRate() == null) {
                resultList.add(new ValidMessage("chargeRate",this.getMessage("FW_E_0006",
                        getMessage("MstBankTranFeeManage002.chargeRate"))));
            }
            // 当计费类型是固定费率时，计费基数必填
            if (StringUtil.equals(dto.getChargeType(), BussinessDictCodeList.计费类型_固定费率.getValue())
                    && StringUtil.isBlank(dto.getChargeBasis())) {
                resultList.add(new ValidMessage("chargeBasis",this.getMessage("FW_E_0006",
                        getMessage("MstBankTranFeeManage002.chargeBasis"))));
            }
            // 最大金额不允许小于最小金额
            if (dto.getFeeMini() != null && dto.getFeeMaxi() != null
                    && dto.getFeeMini().compareTo(dto.getFeeMaxi()) == 1) {
                resultList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0004",
                        getMessage("MstBankTranFeeManage001.feeMaxi") + ":" + dto.getFeeMaxi(),
                        getMessage("MstBankTranFeeManage001.feeMini") + ":" + dto.getFeeMini())));
            }
            // 费用值不允许小于0
            if (dto.getChargeValue() != null
                    && dto.getChargeValue().compareTo(BigDecimal.valueOf(0)) == -1) {
                resultList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0004",
                        getMessage("MstBankTranFeeManage002.chargeValue") + ":" + dto.getChargeValue(),
                        "0")));
            }
            // 费用值不允许小于0
            if (dto.getChargeRate() != null
                    && dto.getChargeRate().compareTo(BigDecimal.valueOf(0)) == -1) {
                resultList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0004",
                        getMessage("MstBankTranFeeManage002.chargeRate") + ":" + dto.getChargeRate(),
                        "0")));
            }
            // 最小金额不允许小于0
            if (dto.getFeeMini() != null
                    && dto.getFeeMini().compareTo(BigDecimal.valueOf(0)) == -1) {
                resultList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0004",
                        getMessage("MstBankTranFeeManage001.feeMini") + ":" + dto.getFeeMini(), "0")));
            }
            // 最大金额不允许小于0
            if (dto.getFeeMaxi() != null
                    && dto.getFeeMaxi().compareTo(BigDecimal.valueOf(0)) == -1) {
                resultList.add(new ValidMessage(this.getMessage("BUSS_COM_E_0004",
                        getMessage("MstBankTranFeeManage001.feeMaxi") + ":" + dto.getFeeMaxi(), "0")));
            }
            // modified by wangguoxian 银行间费用设置 20190718 start
            Map<String, Object> param = new HashMap<>();
            String tradeType = dto.getTradeType();
            String batchSeq = StringUtil.equals(dto.getPageModel(),
                    GlobalConstant.DETAIL_PAGE_MODEL_NEW) ? StringUtil.EMPTY : dto.getBatchSeq();
            param.put("tradeType", tradeType);
            param.put("batchSeq", batchSeq);
            String effDateStr = DateUtil.format(dto.getEffDate(), "yyyy-MM-dd HH:mm:ss");
            param.put("effDate", effDateStr);
            param.put("isMlti",dto.getIsMlti());
            if (StringUtil.isNotBlank(dto.getPs())) {
                for (String ps : dto.getPs().split(",")) {
                    param.put("ps", ps);
                    param.put("feeType", dto.getFeeType());
                    param.put("TRADE_PLACE_01", BussinessDictCodeList.交易场所_银行间.getValue());
                    param.put("registOrg", dto.getRegistOrg());
                    if (StringUtil.equals(tradeType, BussinessDictCodeList.平安资产类型_回购.getValue())
                            || StringUtil.equals(tradeType,
                            BussinessDictCodeList.平安资产类型_买断式回购.getValue())) {
                        if (StringUtil.isNotBlank(dto.getTermType())) {
                            for (String termType : dto.getTermType().split(",")) {
                                param.put("termType", termType);
                                int count = mstBankTranFeeManageService.selExistCnt(param);
                                if (count > 0) {
                                    String psStr = getPsStr(ps);
                                    resultList.add(new ValidMessage(getMessage("MST_INFO_E_0007",
                                            SysDataUtil.getNameFromDataDict(BussinessDictCode.平安资产类型,
                                                    dto.getTradeType()),
                                            psStr, SysDataUtil.getNameFromDataDict(
                                                    BussinessDictCode.期限品种, termType), effDateStr)));
                                }
                            }
                        }
                    } else {
                        //当为债券时，增加交易市场校验
                        param.put("tradeMarketMode",dto.getTradeMarketMode());
                        int count = mstBankTranFeeManageService.selExistCnt(param);
                        if (count > 0) {
                            String psStr = getPsStr(ps);
                            resultList.add(new ValidMessage(
                                    getMessage("MST_INFO_E_0009", SysDataUtil.getNameFromDataDict(
                                            BussinessDictCode.平安资产类型, dto.getTradeType()),
                                            SysDataUtil.getNameFromDataDict(DictCode.交易市场, dto.getTradeMarketMode()),
                                            psStr, effDateStr)));
                        }

                    }

                }
                // modified by wangguoxian 银行间费用设置 20190718 end
            }

            //Add  by  张年  20190829  start
            //获取页面数据集合
            List<MstBankTranFeeManageDto> list = dto.getSelectedItem();
            //判断新建或修改的数据跟流程表中是否冲突
            WflValidInputParameter parameter = new WflValidInputParameter();
            parameter.setPageId(dto.getPageId());
            List<MstBankTranFeeManageDto> workList = mstBankTranFeeManageService.getBusinessData(parameter);
            List<MstBankTranFeeManageDto> data1List = new ArrayList<MstBankTranFeeManageDto>();
            //update by 张年 20190830 start
            if (workList != null && workList.size() > 0 && list != null && list.size() > 0) {
                for (MstBankTranFeeManageDto en : workList) {
                    if (en.getAllData() != null && !StringUtil.equals(en.getAllData(), "")) {
                        try {
                            List<MstBankTranFeeManageDto> data0List = JsonUtils.json2List(en.getAllData(), MstBankTranFeeManageDto.class);
                            //原先组装方法pk1过长
                            if (null != data0List && !data0List.isEmpty()) {
                                //交易方向
                                String[] arrPs = data0List.get(0).getPs().split(",");
                                for (String ps : arrPs) {
                                    MstBankTranFeeManageDto mstBankTranFeeManageDto = new MstBankTranFeeManageDto();
                                    mstBankTranFeeManageDto.setFeeType(data0List.get(0).getFeeType());
                                    mstBankTranFeeManageDto.setRegistOrg(data0List.get(0).getRegistOrg());
                                    mstBankTranFeeManageDto.setTradeType(data0List.get(0).getTradeType());
                                    mstBankTranFeeManageDto.setEffDate(data0List.get(0).getEffDate());
                                    mstBankTranFeeManageDto.setPs(ps);
                                    mstBankTranFeeManageDto.setWorkflowId(en.getWorkflowId());
                                    mstBankTranFeeManageDto.setIsMlti(data0List.get(0).getIsMlti());
                                    if(StringUtil.isNotEmpty(data0List.get(0).getTradeMarketMode())){
                                        mstBankTranFeeManageDto.setTradeMarketMode(data0List.get(0).getTradeMarketMode());
                                    }
                                    if(StringUtil.isNotEmpty(data0List.get(0).getTermType())){
                                        //期限品种
                                        String[] arrTerm = data0List.get(0).getTermType().split(",");
                                        for (String termType : arrTerm) {
                                            mstBankTranFeeManageDto.setTermType(termType);
                                            data1List.add(mstBankTranFeeManageDto);
                                        }
                                    }else{
                                        data1List.add(mstBankTranFeeManageDto);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(),e);
                        }
                    }
                }
                //update by 张年 20190830 end
                //定义一个全局开关
                boolean flag2 = false;
                for (MstBankTranFeeManageDto d : list) {
                    if (flag2) {
                        break;
                    }
                    //如果交易品种相同，交易方向相同并且，工作流代码不相同(即不是同一工作流程中的的数据)
                    for (MstBankTranFeeManageDto data0 : data1List) {
                        if (StringUtil.equals(d.getTradeType(), data0.getTradeType())
                                && StringUtil.equals(d.getPs(), data0.getPs())
                                && StringUtil.equals(d.getFeeType(), data0.getFeeType())
                                && StringUtil.equals(d.getRegistOrg(), data0.getRegistOrg())
                                //增加流程中数据对期限品种和是否多券种的判断 add by gejiaao 20191230 start
                                && StringUtil.equals(d.getTermType(), data0.getTermType())
                                && StringUtil.equals(d.getIsMlti(), data0.getIsMlti())
                                //增加流程中数据对期限品种和是否多券种的判断 add by gejiaao 20191230 end
                                && DateUtil.compareDate(d.getEffDate(), data0.getEffDate()) == 0
                                && StringUtil.equals(d.getTradeMarketMode(),data0.getTradeMarketMode())
                                && !StringUtil.equals(data0.getWorkflowId(), dto.getWorkflowId())) {
                            flag2 = true;
                            break;
                        }
                    }
                }
                if (flag2) {
                    resultList.add(new ValidMessage(this.getMessage("MST_INFO_E_0004",
                            getMessage("MstBankTranFeeManage001.tradeType") + ":" + dto.getTradeTypeStr(),
                            getMessage("MstBankTranFeeManage001.ps"))));
                }
            }
            //Add  by  张年  20190829  end
          //Add  by  wangpeng  20191101  end
            if(BussinessDictCodeList.费用类型_结算手续费.getValue().equals(dto.getFeeType())
                    && StringUtil.isEmpty(dto.getRegistOrg())){
            	resultList.add(new ValidMessage("registOrg","登记托管机构必填！"));
            }
            
          //Add  by  wangpeng  20191101  end
        }
        return resultList;
    }

    // add by wangguoxian 20190718 start
    /**
     * 获取交易方向名称
     *
     * @param ps 交易方向
     * @return 交易方向名称
     * @see
     * @since 1.0
     */
    private String getPsStr(String ps) {
        String psStr = StringUtil.EMPTY;
        if (ps.startsWith("T")) {
            psStr = SysDataUtil.getNameFromDataDict(BussinessDictCode.交易方向, ps);
        }
        else {
            psStr = SysDataUtil.getNameFromDataDict(BussinessDictCode.回购拆借方向, ps);
        }
        return psStr;
    }
    // add by wangguoxian 20190718 end
}
