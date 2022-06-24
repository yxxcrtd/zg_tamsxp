package com.joyintech.tams.svreport.chb.common.util.asset;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.ChbAssetInfo;
import com.joyintech.tams.common.base.entity.ChbAssetInfoErrodata;
import com.joyintech.tams.common.base.entity.ChbAssetInfoErrodataWithBLOBs;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.svreport.chb.assetinfodetail.dao.AssetInfoDetailMapper;
import com.joyintech.tams.svreport.chb.common.util.ErrdataConvertUtil;
import com.joyintech.tams.svreport.chb.common.util.asset.dto.RechbAassetInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资产要素数据获取共通
 */
@Service
public class RechbAassetInfoUtilService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(RechbAassetInfoUtilService.class);

    @Autowired
    private AssetInfoDetailMapper assetInfoDetailMapper;


    /**
     * 根据uuid和flag查询对应的资产要素实体类
     * @param flag  01-CHB_ASSET_INFO_ERRODATA    02-CHB_ASSET_INFO_
     * @param assetUUid
     * @return
     */
    public   ChbAssetInfo BackChbAssetInfoMethod(String  flag,String assetUUid){
        ChbAssetInfo  infoDto=new  ChbAssetInfo();
        //当 入参【模式】为01的况下 查询CHB_ASSET_INFO_ERRODATA
        if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),flag)){
            //根据UUID 查询数据库对应记录
            List<ChbAssetInfoErrodataWithBLOBs>  listChbAssetInfoErrordata=assetInfoDetailMapper.getChbAssetInfoErrorDataByUUid(assetUUid);
            //调用共通方法，获取资产要素
            if(listChbAssetInfoErrordata!=null&&listChbAssetInfoErrordata.size()>0){
                ErrdataConvertUtil.errdataAssetInfoConvert(infoDto,listChbAssetInfoErrordata.get(0));
                infoDto.setAssetUuid(assetUUid);
            }

        }
        //当 入参【模式】为02的况下
        else {
            infoDto=assetInfoDetailMapper.getChbAssetInfoByUUid(assetUUid);
        }
         return  infoDto;
    }
}
