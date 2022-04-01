package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.MstCustomerInfoAdd;
import com.joyintech.tams.common.base.entity.MstCustomerInfoAddWithBLOBs;
import org.springframework.stereotype.Repository;

@Repository
public interface MstCustomerInfoAddMapper {
    int deleteByPrimaryKey(String customerId);

    int insert(MstCustomerInfoAddWithBLOBs record);

    int insertSelective(MstCustomerInfoAddWithBLOBs record);

    int insertBasicByBasicInfo(MstCustomerInfoAddWithBLOBs record);


    MstCustomerInfoAddWithBLOBs selectByPrimaryKey(String customerId);

    int updateByPrimaryKeySelective(MstCustomerInfoAddWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MstCustomerInfoAddWithBLOBs record);

    int updateByPrimaryKey(MstCustomerInfoAdd record);
}