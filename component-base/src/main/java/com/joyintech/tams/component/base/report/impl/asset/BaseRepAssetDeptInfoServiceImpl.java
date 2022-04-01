/*
 * 文件名：RepAssetDeptCommonService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：huwenbin
 * 修改时间：2018年8月28日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.component.base.report.impl.asset;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.joyintech.tams.common.base.entity.RepAssetDept;
import com.joyintech.framework.base.BaseService;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.component.base.report.asset.BaseRepAssetDeptInfoService;
import com.joyintech.tams.component.base.report.dto.BaseRepAssetDeptInfoDto;
import com.joyintech.tams.component.base.report.impl.dao.BaseRepAssetDeptInfoMapper;

/**
 * 资产负债信息报送组件                                                       
 * 〈功能详细描述〉
 * @author huwenbin
 * @version 1.0 2018年8月28日
 * @see BaseRepAssetDeptInfoServiceImpl
 * @since 1.0
 */
@Service
public class BaseRepAssetDeptInfoServiceImpl extends BaseService<RepAssetDept> implements BaseRepAssetDeptInfoService{
    
    @Autowired
    private BaseRepAssetDeptInfoMapper baseRepAssetDeptInfoMapper;
    
    /**
     * 资产负债信息删除
     */
    @Override
    public boolean deleteByPrimaryKey(BaseRepAssetDeptInfoDto baseRepAssetDeptInfoDto) {
        if(null != baseRepAssetDeptInfoDto){
            baseRepAssetDeptInfoMapper.deleteByPrimaryKeyList(baseRepAssetDeptInfoDto.getFinprodIdList());
        }
        return true;
        
    }

    /**
     * 资产负债信息新建
     */
    @Override
    public boolean insert(List<RepAssetDept> repAssetDeptList) {
        if(repAssetDeptList!=null && repAssetDeptList.size()>0){
            for(RepAssetDept repAssetDept:repAssetDeptList){
                baseRepAssetDeptInfoMapper.insert(repAssetDept);
            }
        }
        return true;
    }

    /**
     * 资产负债信息更新
     */
    @Override
    public boolean updateBaseRepAssetDeptInfoDto(BaseRepAssetDeptInfoDto baseRepAssetDeptInfoDto) {
        baseRepAssetDeptInfoMapper.deleteByPrimaryKeyList(baseRepAssetDeptInfoDto.getFinprodIdList());
        for(RepAssetDept repAssetDept:baseRepAssetDeptInfoDto.getRepAssetDeptList()){
            baseRepAssetDeptInfoMapper.insert(repAssetDept);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RepAssetDept> selectRepAssetDeptInfo(BaseRepAssetDeptInfoDto dto) {
        return baseRepAssetDeptInfoMapper.selectRepAssetDeptInfo(dto);
    }
    
}
