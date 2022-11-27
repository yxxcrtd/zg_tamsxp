package com.joyintech.tams.invest.risk.valtblimport.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.common.service.InvestInterfCommService;
/*import com.joyintech.tams.invest.risk.valtblimport.dao.RskValTableQueryMapper;*/
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblAssetSubDto;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblAssetSumDto;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblFileInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RskValTableQueryService extends CommonExportService<RskValtblAssetSumDto>{
    /**
     * 投管接口共通组件
     */
    @Autowired
    private InvestInterfCommService investInterfCommService;
    /*@Autowired
    private RskValTableQueryMapper rskValTableQueryMapper;*/
    public List<SelectOptionDto> getFileName(String fileName){
        /*return rskValTableQueryMapper.getFileName(fileName);*/
        return restTemplate.getForList(ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/getFileName?fileName="+fileName,
                SelectOptionDto.class);
    }
    public List<SelectOptionDto> getFileHeader(String fileHeader){
        return restTemplate.getForList(ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/getFileHeader?fileHeader="+fileHeader,
                SelectOptionDto.class);
    }
    public List<SelectOptionDto> getSubCode(String subCode){
        return restTemplate.getForList(ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/getSubCode?subCode="+subCode,
                SelectOptionDto.class);
    }

    public Page<RskValtblFileInfoDto> selectForPage(Map<String, Object> pageParam) {
        return restTemplate.postForPage(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/findPage",
                pageParam, RskValtblFileInfoDto.class);
    }

    public List<RskValtblAssetSubDto> selectRskValTableDetaile(Map<String, Object> param) {
        return restTemplate.postForList(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/selectRskValTableDetaile",
                param, RskValtblAssetSubDto.class);
    }

    @Override
    protected void buildExportParam(ExportParam exportParam) {
            exportParam.config("/com/joyintech/tams/invest/risk/valtblimport/conf/export_conf.xml")
                    .dataSource(DataSourceType.MASERT);
    }

    /**
     * {@inheritDoc}
     */
   /* @Override
    public Page<RskValtblAssetSumDto> selectPageData(Map<String, Object> selectParam) {
        return selectForPage(selectParam);
    }*/
    /**
     * 发送【第三方接口】
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    public void send(Map<String, Object> pageParam) {
        //获取数据
        Page<RskValtblAssetSumDto> rskValtblAssetSumDtoPage = restTemplate.postForPage(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/findPage",
                pageParam, RskValtblAssetSumDto.class);

        if(!StringUtil.isEmptyList(rskValtblAssetSumDtoPage.getRoot())) {
            Map<String, Object> s060Map = JsonUtils.object2Map(rskValtblAssetSumDtoPage.getRoot());
            s060Map.put("pageId", "RofvBottomValuation");
            investInterfCommService.doInvestInterf(s060Map);
        }
    }
}
