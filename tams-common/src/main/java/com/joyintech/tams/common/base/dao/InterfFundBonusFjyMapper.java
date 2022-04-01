package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.InterfFundBonusFjy;

public interface InterfFundBonusFjyMapper {
    int deleteByPrimaryKey(String seqno);

    int insert(InterfFundBonusFjy record);

    int insertSelective(InterfFundBonusFjy record);

    InterfFundBonusFjy selectByPrimaryKey(String seqno);

    int updateByPrimaryKeySelective(InterfFundBonusFjy record);

    int updateByPrimaryKey(InterfFundBonusFjy record);
}