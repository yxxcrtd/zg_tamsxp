/*
 * 文件名：SysInstRegBankCode002Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：刘二通
 * 修改时间：2018年5月18日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.report.system.instregbankcode.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.ChbOrgRegistBankCodeMapper;
import com.joyintech.tams.common.base.entity.ChbOrgRegistBankCode;
import com.joyintech.tams.report.system.instregbankcode.dao.SysInstRegBankCode002Mapper;
import com.joyintech.tams.report.system.instregbankcode.dto.SysInstRegBankCode002Dto;
import com.joyintech.tams.constant.DictCodeListExt1;

/**
 * 机构登记银行代码管理画面用service
 *
 * @author 刘二通
 * @version 1.0 2018年5月18日
 * @see SysInstRegBankCode002Service
 * @since 1.0
 */
@Service
public class SysInstRegBankCode002Service extends BaseService<SysInstRegBankCode002Dto> {

    /**
     * ChbOrgRegistBankCodeMapper
     */
    @Autowired
    private ChbOrgRegistBankCodeMapper chbOrgRegistBankCodeMapper;

    /**
     * SysInstRegBankCode002Mapper
     */
    @Autowired
    private SysInstRegBankCode002Mapper sysInstRegBankCode002Mapper;

    /**
     * 根据主键信息查询数据
     *
     * @param paramMap 参数map
     * @return 数据
     */
    public SysInstRegBankCode002Dto getInfoData(Map<String,Object> paramMap) {
        return sysInstRegBankCode002Mapper.selectPageData(paramMap);
    }

    /**
     * 新建页面的数据保存操作
     * @param inputDto 页面数据
     * @see
     * @since 1.0
     */
    public void saveData(SysInstRegBankCode002Dto inputDto) {
        ChbOrgRegistBankCode chbOrgRegistBankCode = new ChbOrgRegistBankCode();
        chbOrgRegistBankCode.setOrgCode(inputDto.getOrgCode());
        chbOrgRegistBankCode.setRegistBankCode(inputDto.getRegistBankCode());
        chbOrgRegistBankCode.setCreateDept(inputDto.getOrgCode());
        chbOrgRegistBankCode.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
        /*机构登记银行代码管理增加总行机构代码和是否理财子公司字段 add by gaojian 2019-06-05 begin*/
        chbOrgRegistBankCode.setPbcBankCode(inputDto.getPbcBankCode());
        chbOrgRegistBankCode.setIsFinSubCompany(inputDto.getIsFinSubCompany());
        chbOrgRegistBankCode.setInsitituteCode(inputDto.getInsitituteCode());
        /*机构登记银行代码管理增加总行机构代码和是否理财子公司字段 add by gaojian 2019-06-05 end*/
        /**新增所属银保监局字段   20220307 leipeng****/
        chbOrgRegistBankCode.setBelogCodeCBRC(inputDto.getBelogCodeCBRC());
        /**新增所属银保监局字段   20220307 leipeng****/
        if (StringUtil.equals(inputDto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_NEW)) {
            chbOrgRegistBankCodeMapper.insert(chbOrgRegistBankCode);
        }
        else {
            chbOrgRegistBankCodeMapper.updateByPrimaryKey(chbOrgRegistBankCode);
        }
    }
}
