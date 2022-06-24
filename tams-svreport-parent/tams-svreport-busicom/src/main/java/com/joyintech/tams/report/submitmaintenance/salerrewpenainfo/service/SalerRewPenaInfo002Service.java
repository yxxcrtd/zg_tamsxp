package com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.dao.ChbSalerRewPenaInfoMapper;
import com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.dto.SalerRewPenaInfoDto;
import com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.util.RewPenaInfoErrdataConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("SalerRewPenaInfo002Service")
public class SalerRewPenaInfo002Service extends BaseService{

    @Autowired
    private ChbSalerRewPenaInfoMapper chbSalerRewPenaInfoMapper;

    /**
     * 双击查看详细信息查询 修改页面信息查询
     *
     */
    public SalerRewPenaInfoDto selectDetail(Map<String,Object> param) {
        String erroOrInfo = param.get("erroOrInfo").toString();
        if (erroOrInfo.equals("info")) {
            //来自info表
            return chbSalerRewPenaInfoMapper.selectDetail(param);
        } else {
            //来自erro表
            SalerRewPenaInfoDto infoDto = new SalerRewPenaInfoDto();
            List<SalerRewPenaInfoDto> listSalerErrordata = chbSalerRewPenaInfoMapper.selectErroDetail(param);
            if (listSalerErrordata != null && listSalerErrordata.size() > 0) {
                SalerRewPenaInfoDto salerRewPenaInfoDto = listSalerErrordata.get(0);
                RewPenaInfoErrdataConvertUtil.errdataAssetInfoConvert(infoDto, salerRewPenaInfoDto);
                infoDto.setSalerRewPenaNo(salerRewPenaInfoDto.getSalerRewPenaNo());
                infoDto.setErroOrInfo("erro");
            }
            return infoDto;
        }
    }

    /**
     * 新建保存
     * @param
     */
    public void saveAndSubmit(SalerRewPenaInfoDto dto) {
        dto.setStatus("E");
        dto.setSalerRewPenaNo(UUID.randomUUID().toString().replace("-","").toUpperCase());
        chbSalerRewPenaInfoMapper.insert(dto);
    }

    /**
     * 修改保存
     * @param
     */
    public void update(SalerRewPenaInfoDto dto) {
        //来自info表的直接更新数据
        if(dto.getErroOrInfo().equals("info")){
            dto.setStatus("E");
            //更新代理销售机构信息
            chbSalerRewPenaInfoMapper.updateByPrimaryKeySelective(dto);
        }
        //来自erro表的先删除erro表数据再插入info表
        else{
            chbSalerRewPenaInfoMapper.deleteErroByPrimaryKey(dto.getSalerRewPenaNo());
            this.saveAndSubmit(dto);
        }
    }

    /**
     * 内部编号校验
     * @param salerInsNo
     * @return
     */
    public int checkSalerInsNo(String salerInsNo){
        return  chbSalerRewPenaInfoMapper.checkSalerInsNo(salerInsNo);
    }

    /**
     * 查询发行机构代码
     * @param orgCode
     * @return
     */
    public String selectIssuePartyId(String orgCode){
        return  chbSalerRewPenaInfoMapper.selectIssuePartyId(orgCode);
    }

    /**
     * 查询姓名
     * @param salerInsNo
     * @return
     */
    public String getSalerName(String salerInsNo){
        return  chbSalerRewPenaInfoMapper.getSalerName(salerInsNo);
    }
}