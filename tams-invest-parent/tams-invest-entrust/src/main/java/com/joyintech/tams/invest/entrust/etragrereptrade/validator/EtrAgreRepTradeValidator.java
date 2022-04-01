package com.joyintech.tams.invest.entrust.etragrereptrade.validator;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.FinProductMapper;
import com.joyintech.tams.common.base.entity.FinProduct;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.systemtools.interf.IPlatformDate;
import com.joyintech.tams.invest.entrust.common.validator.EtrCommonValidator;
import com.joyintech.tams.invest.entrust.etragrereptrade.dto.EtrAgreRepTradeDto;
import com.joyintech.tams.invest.entrust.etragrereptrade.service.EtrAgreRepTrade001Service;
import com.joyintech.tams.invest.entrust.etrexchangetrade.service.EtrExchangeTrade001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class EtrAgreRepTradeValidator implements EtrCommonValidator {

    @Autowired
    private IPlatformDate iPlatformDate;

    @Autowired
    private FinProductMapper finProductMapper;

    @Autowired
    private EtrExchangeTrade001Service etrExchangeTrade001Service;

    @Autowired
    private EtrAgreRepTrade001Service etrAgreRepTrade001Service;

    @Override
    public List<ValidMessage> bussValid(Object value) {
        List<ValidMessage> errorList = new ArrayList<>();
        EtrAgreRepTradeDto dto = (EtrAgreRepTradeDto)value;

        //交易方向Map
        Map<String,DictCodeListExt3> psMap = new HashMap<String,DictCodeListExt3>() {
            {
                put(DictCodeListExt3.交易方向_提前购回.getValue(),DictCodeListExt3.交易方向_提前购回);
                put(DictCodeListExt3.交易方向_到期购回.getValue(),DictCodeListExt3.交易方向_到期购回);
                put(DictCodeListExt3.交易方向_中途换券.getValue(),DictCodeListExt3.交易方向_中途换券);
                put(DictCodeListExt3.交易方向_解除质押.getValue(),DictCodeListExt3.交易方向_解除质押);
                put(DictCodeListExt3.交易方向_到期续做.getValue(),DictCodeListExt3.交易方向_到期续做);
            }
        };

        FinProduct finProduct = finProductMapper.selectByPrimaryKey(dto.getFinprodId());
        if(StringUtil.isNotEmpty(finProduct) && DateUtil.compareTo(DateUtil.parse(dto.getExpireSettleDate()),finProduct.getMdate())>0) {
            errorList.add(new ValidMessage("term", "回购期限不能超过质押债券的存续期限！"));
        }

        //判断提前购回、到期购回、到期续作、中途换券、解除质押需要判断回购持仓
        if(StringUtil.isNotEmpty(psMap.get(dto.getPs())) && !StringUtil.equals(dto.getRedoType(),"1")) {

            Map<String, Object> param = new HashMap<String, Object>();
            param.put("tradeId",StringUtil.isNotEmpty(dto.getTradeId())?dto.getTradeId():dto.getIntiTradeId());
            EtrAgreRepTradeDto firstEtrAgreRepTradeDto= etrAgreRepTrade001Service.getEntrustInfoByTradeId(param);
            if(!StringUtil.isNotEmpty(firstEtrAgreRepTradeDto)) {
                errorList.add(new ValidMessage("", "未找到质押式回购首期信息！"));
            }
            else {
                param.put("portfolioId", dto.getPortfolioId());
                param.put("finprodId", firstEtrAgreRepTradeDto.getTradeId());
                //if (null != dto.getSecAcctId()) {
                //    param.put("secAcctId", dto.getSecAcctId());
               // }
//            param.put("secAcctId", "999999");
                param.put("ccy", DictCodeList.币种_人民币_CNY.getValue());
                BigDecimal availableNum =  etrExchangeTrade001Service.selectAvailableNum(param);
                if(StringUtil.isNotEmpty(availableNum) && BigDecimal.ZERO.compareTo(availableNum)>=0) {
                    errorList.add(new ValidMessage("", psMap.get(dto.getPs()).getCodeName()+"不能重复做！"));
                }
            }

        }

//        if(StringUtil.equals(dto.getTradeMarket(), DictCodeListExt1.交易场所_上海证券交易所.getValue())) {
//
//        }

        if(StringUtil.equals(dto.getTradeMarket(), DictCodeListExt1.交易场所_深圳证券交易所.getValue())) {
            if(StringUtil.equals(dto.getPs(),DictCodeListExt3.交易方向_到期续做.getValue())) {
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("tradeId",dto.getTradeId() == null ? dto.getIntiTradeId() : dto.getTradeId());
                EtrAgreRepTradeDto firstEtrAgreRepTradeDto = etrAgreRepTrade001Service.getEntrustInfoByTradeId(queryMap);
                if(StringUtil.isNotEmpty(firstEtrAgreRepTradeDto)) {
                    if(StringUtil.isNotEmpty(firstEtrAgreRepTradeDto.getTerm()) && firstEtrAgreRepTradeDto.getTerm().compareTo(dto.getTerm())>=0) {
                        errorList.add(new ValidMessage("term", "回购期限必须大于原回购期限！"));
                    }
                }
            }
        }




        return errorList;
    }
}
