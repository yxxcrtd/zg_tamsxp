package com.joyintech.tams.svreport.interf.chb.baseinfo.dao;

import java.util.List;
import java.util.Map;

import com.joyintech.system.base.entity.SysDatadictListLink;
import com.joyintech.tams.common.base.entity.MstCustomerInfo;
import com.joyintech.tams.common.base.entity.MstCustomerInfoAddWithBLOBs;
import org.apache.ibatis.annotations.Param;

import com.joyintech.tams.common.base.entity.MstCustomerInfoAdd;
import org.springframework.stereotype.Repository;

@Repository
public interface SvrMstCustomerInfoAddImpMapper {
    int deleteByPrimaryKey(String customerId);

    int insert(MstCustomerInfoAdd record);

    int insertSelective(MstCustomerInfoAdd record);

    MstCustomerInfoAdd selectByPrimaryKey(String customerId);

    int updateByPrimaryKeySelective(MstCustomerInfoAdd record);

    int updateByPrimaryKey(MstCustomerInfoAdd record);
    
    int deleteCustomerAddBatch(@Param("list")List<MstCustomerInfoAddWithBLOBs> record);
    
    int deleteAllData();
    
    int mergeMstCustomerInfoData(@Param("list")List<MstCustomerInfoAddWithBLOBs> record);
    
    int deleteTodayData();

    /**
     * 查询所有交易对手 信息
     * @return
     */
    List<MstCustomerInfoAdd> getAllMstCustomerInfoInstit();

    /**
     * 查询所有发行机构信息
     * @return
     */
    List<MstCustomerInfo> getAllCustomerInfo();

    //获取行业二级分类与一级分类的映射关系
    List<SysDatadictListLink> getAllIndustryShares();
}