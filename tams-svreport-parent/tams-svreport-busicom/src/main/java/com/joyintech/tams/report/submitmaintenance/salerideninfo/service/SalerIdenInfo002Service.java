package com.joyintech.tams.report.submitmaintenance.salerideninfo.service;

import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.framework.base.BaseService;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.dao.ChbSalerIdenInfoMapper;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.dto.SalerIdenInfoDto;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.util.IdenInfoErrdataConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("SalerIdenInfo002Service")
public class SalerIdenInfo002Service extends BaseService{

    @Autowired
    private ChbSalerIdenInfoMapper chbSalerIdenInfoMapper;

    /**
     * 双击查看详细信息查询 修改页面信息查询
     *
     */
    public SalerIdenInfoDto selectDetail(Map<String,Object> param) throws Exception{
        String erroOrInfo = param.get("erroOrInfo").toString();
        if (erroOrInfo.equals("info")) {
            //来自info表
            SalerIdenInfoDto dto = chbSalerIdenInfoMapper.selectDetail(param);
            this.formateTime(dto);
            return dto;
        } else {
            //来自erro表
            SalerIdenInfoDto infoDto = new SalerIdenInfoDto();
            List<SalerIdenInfoDto> listSalerErrordata = chbSalerIdenInfoMapper.selectErroDetail(param);
            if (listSalerErrordata != null && listSalerErrordata.size() > 0) {
                SalerIdenInfoDto salerIdenInfoDto = listSalerErrordata.get(0);
                IdenInfoErrdataConvertUtil.errdataAssetInfoConvert(infoDto, salerIdenInfoDto);
                infoDto.setWorkflowId(salerIdenInfoDto.getWorkflowId());
                infoDto.setErroOrInfo("erro");
            }
            this.formateTime(infoDto);
            return infoDto;
        }
    }

    /**
     * 新建保存
     * @param
     */
    public void saveAndSubmit(SalerIdenInfoDto dto) {

        //check表中是否存在已逻辑删除的数据
        if(chbSalerIdenInfoMapper.getByPrimaryKeyCount(dto.getSalerInsNo())>0){
            //如果存在就先 删除表中删除状态数据再 新增插入
            chbSalerIdenInfoMapper.deleteByPrimaryKey(dto.getSalerInsNo());
        }
        dto.setStatus("E");
        dto.setSalerWorkStartYmd(dto.getSalerWorkStartYmd().replace("-", "").replace("/", ""));
        dto.setSalerFinanceStartYmd(dto.getSalerFinanceStartYmd().replace("-", "").replace("/", ""));
        dto.setSalerStartYmd(dto.getSalerStartYmd().replace("-", "").replace("/", ""));
        chbSalerIdenInfoMapper.insert(dto);
    }

    /**
     * 修改保存
     * @param
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(SalerIdenInfoDto dto) {
        //来自info表的直接更新数据
        if(dto.getErroOrInfo().equals("info")){
            dto.setSalerWorkStartYmd(dto.getSalerWorkStartYmd().replace("-", "").replace("/", ""));
            dto.setSalerFinanceStartYmd(dto.getSalerFinanceStartYmd().replace("-", "").replace("/", ""));
            dto.setSalerStartYmd(dto.getSalerStartYmd().replace("-", "").replace("/", ""));
            dto.setStatus("E");
            //更新代理销售机构信息
            chbSalerIdenInfoMapper.updateByPrimaryKeySelective(dto);
        }
        //来自erro表的先删除erro表数据再插入info表
        else{
            chbSalerIdenInfoMapper.deleteErroByPrimaryKey(dto.getWorkflowId());
            this.saveAndSubmit(dto);
        }
    }

    /**
     * 内部编码重复校验
     * @param salerInsNo
     * @return
     */
    public int checkSalerInsNo(String salerInsNo){
        return  chbSalerIdenInfoMapper.checkSalerInsNo(salerInsNo);
    }

    /**
     * 证件号码重复校验
     * @param salerCredentType,salerOtherCredentName,salerCredentNo
     * @return
     */
    public int checkSalerCredentNo(String salerCredentType,String salerOtherCredentName,String salerCredentNo){
        return  chbSalerIdenInfoMapper.checkSalerCredentNo(salerCredentType,salerOtherCredentName,salerCredentNo);
    }

    /**
     * 查询发行机构代码
     * @param orgCode
     * @return
     */
    public String selectIssuePartyId(String orgCode){
        return  chbSalerIdenInfoMapper.selectIssuePartyId(orgCode);
    }

    /**
     * 所属总部或分支机构校验有效性
     * @param salerOrgCode
     * @return
     */
    public int checkSalerOrgCode(String salerOrgCode){
        return  chbSalerIdenInfoMapper.checkSalerOrgCode(salerOrgCode);
    }

    //处理YYYY-MM的时间
    public void formateTime(SalerIdenInfoDto dto) throws Exception {
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter2  = new SimpleDateFormat("yyyyMM");
        //首次参加工作时间
        if(StringUtil.isNotEmpty(dto.getSalerWorkStartYmd())){
            String SalerWorkStartYmd = dto.getSalerWorkStartYmd();
            Date date = formatter2.parse(SalerWorkStartYmd);
            String  dString = formatter.format(date);
            dto.setSalerWorkStartYmd(dString);
        }
        //首次从事金融工作时间
        if(StringUtil.isNotEmpty(dto.getSalerFinanceStartYmd())){
            String SalerFinanceStartYmd = dto.getSalerFinanceStartYmd();
            Date date = formatter2.parse(SalerFinanceStartYmd);
            String  dString = formatter.format(date);
            dto.setSalerFinanceStartYmd(dString);
        }
       //首次从事理财销售时间
        if(StringUtil.isNotEmpty(dto.getSalerStartYmd())){
            String SalerStartYmd = dto.getSalerStartYmd();
            Date date = formatter2.parse(SalerStartYmd);
            String  dString = formatter.format(date);
            dto.setSalerStartYmd(dString);
        }
    }

}