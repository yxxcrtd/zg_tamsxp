package com.joyintech.tams.report.submitmaintenance.salerinstinfo.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.tams.report.submitmaintenance.salerinstinfo.dao.ChbSalerInstInfoMapper;
import com.joyintech.tams.report.submitmaintenance.salerinstinfo.dto.SalerInstInfoDto;
import com.joyintech.tams.report.submitmaintenance.salerinstinfo.util.InstiInfoErrdataConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("SalerInstInfo002Service")
public class SalerInstInfo002Service extends BaseService{

    @Autowired
    private ChbSalerInstInfoMapper chbSalerInstInfoMapper;

    /**
     * 双击查看详细信息查询 修改页面信息查询
     *
     */
    public SalerInstInfoDto selectDetail(Map<String,Object> param) {
        String erroOrInfo = param.get("erroOrInfo").toString();
        if (erroOrInfo.equals("info")) {
            //来自info表
            return chbSalerInstInfoMapper.selectDetail(param);
        } else {
            //来自erro表
            SalerInstInfoDto infoDto = new SalerInstInfoDto();
            List<SalerInstInfoDto> listSalerErrordata = chbSalerInstInfoMapper.selectErroDetail(param);
            if (listSalerErrordata != null && listSalerErrordata.size() > 0) {
                SalerInstInfoDto salerInstInfoDto = listSalerErrordata.get(0);
                InstiInfoErrdataConvertUtil.errdataAssetInfoConvert(infoDto, salerInstInfoDto);
                infoDto.setWorkflowId(salerInstInfoDto.getWorkflowId());
                infoDto.setErroOrInfo("erro");
            }
            return infoDto;
        }
    }

    /**
     * 新建保存
     * @param
     */
    public void saveAndSubmit(SalerInstInfoDto dto) {
        //check表中是否存在的数据
        if(chbSalerInstInfoMapper.getSalerInstInfoCount(dto.getSalerOrgCode())>0){
            //如果存在就先 删除表中删除状态数据再 新增插入
            chbSalerInstInfoMapper.deleteByPrimaryKey(dto.getSalerOrgCode());
        }
        dto.setStatus("E");
        chbSalerInstInfoMapper.insert(dto);
    }

    /**
     * 修改保存
     * @param
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(SalerInstInfoDto dto) {
        //来自info表的直接更新数据
        if(dto.getErroOrInfo().equals("info")){
        dto.setStatus("E");
        //更新代理销售机构信息
        chbSalerInstInfoMapper.update(dto);
        }
        //来自erro表的先删除erro表数据再插入info表
        else{
            chbSalerInstInfoMapper.deleteErroByPrimaryKey(dto.getWorkflowId());
            this.saveAndSubmit(dto);
        }
    }

    /**
     * 总部或分支机构编码重复校验
     * @param salerOrgCode
     * @return
     */
    public int checkSalerOrgCode(String salerOrgCode){
        return  chbSalerInstInfoMapper.checkSalerOrgCode(salerOrgCode);
    }

    /**
     * 总部或分支机构名称重复校验(新建)
     * @param salerOrgName
     * @return
     */
    public int checkSalerOrgName(String salerOrgName){
        return  chbSalerInstInfoMapper.checkSalerOrgName(salerOrgName);
    }

    /**
     * 总部或分支机构名称重复校验（编辑）
     * @param salerOrgName
     * @return
     */
    public int checkSalerOrgNameEdit(String salerOrgName ,String salerOrgCode){
        return  chbSalerInstInfoMapper.checkSalerOrgNameEdit(salerOrgName,salerOrgCode);
    }

    /**
     * 查询发行机构代码
     * @param orgCode
     * @return
     */
    public String selectIssuePartyId(String orgCode){
        return  chbSalerInstInfoMapper.selectIssuePartyId(orgCode);
    }
}