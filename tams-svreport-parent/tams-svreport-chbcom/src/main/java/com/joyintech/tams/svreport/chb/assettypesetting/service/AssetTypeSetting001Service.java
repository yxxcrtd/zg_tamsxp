package com.joyintech.tams.svreport.chb.assettypesetting.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.ChbAssetTypeMappingExMapper;
import com.joyintech.tams.common.base.dao.ChbAssetTypeMappingMapper;
import com.joyintech.tams.common.base.entity.ChbAssetTypeMapping;
import com.joyintech.tams.common.base.entity.ChbAssetTypeMappingEx;
import com.joyintech.tams.report.submitmaintenance.agentinstitution.dto.AgentInstitutionDto;
import com.joyintech.tams.svreport.chb.assettypesetting.dao.AssetTypeSetting001Mapper;
import com.joyintech.tams.svreport.chb.assettypesetting.dto.AssetTypeSetting001Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class AssetTypeSetting001Service extends CommonExportService<AssetTypeSetting001Dto> {

    @Autowired
    private AssetTypeSetting001Mapper assetTypeSetting001Mapper;

    @Autowired
    private ChbAssetTypeMappingMapper chbAssetTypeMappingMapper;

    @Autowired
    private ChbAssetTypeMappingExMapper chbAssetTypeMappingExMapper;

    private String elementId = "AssetTypeSetting001_export";

    /**
     * 分页查询
     * @param param
     * @return
     */
    public Page<AssetTypeSetting001Dto> selectForPage(Map<String, Object> param) {
        Page<AssetTypeSetting001Dto> page = queryForPage(AssetTypeSetting001Mapper.class,"selectForPage",param);;
        return page;
    }

    /**
     * 点击左侧列表数据事件
     * @param param
     * @return
     */
    public Page<AssetTypeSetting001Dto> selectById(Map<String, Object> param) {
        Page<AssetTypeSetting001Dto> page = queryForPage(AssetTypeSetting001Mapper.class,"selectById",param);;
        return page;
    }

    /**
     * 新建保存
     * @param dto
     * @return
     */
    public Integer doSave(AssetTypeSetting001Dto dto) {
        int  flag = 0;
        if (StringUtil.isNotEmpty(dto)){
            if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_NEW)){//新建
                ChbAssetTypeMapping chbAssetTypeMapping = new ChbAssetTypeMapping();
                List<ChbAssetTypeMappingEx> chbAssetTypeMappingExList = new ArrayList<>();
                if(StringUtil.isNotEmpty(dto)){
                    //资产负债类别映射表
                    String typeuuid = IDGenerator.generate();
                    chbAssetTypeMapping.setTypeuuid(typeuuid);
                    chbAssetTypeMapping.setAssetType1(dto.getAssetType1());
                    chbAssetTypeMapping.setAssetType2(dto.getAssetType2());
                    chbAssetTypeMapping.setAssetType3(dto.getAssetType3());
                    chbAssetTypeMapping.setAssetBusiType(dto.getAssetBusiType());
                    flag = chbAssetTypeMappingMapper.insert(chbAssetTypeMapping);
                    //资产负债类别映射附属表
                    chbAssetTypeMappingExList = dto.getSelectedItem1();
                    if (StringUtil.isNotEmpty(chbAssetTypeMappingExList)){
                        for (ChbAssetTypeMappingEx entity: chbAssetTypeMappingExList) {
                            String typeuuidEx = IDGenerator.generate();
                            entity.setTypeuuid(typeuuidEx);
                            entity.setMappingUuid(typeuuid);
                            chbAssetTypeMappingExMapper.insert(entity);
                        }
                    }
                }
            }
            if (StringUtil.equals(dto.getPageModel(),GlobalConstant.DETAIL_PAGE_MODEL_EDIT)){//编辑 全删全插
                String typeuuid = dto.getTypeuuid();
                //根据typeuuid删除资产负债类别映射表的数据
                chbAssetTypeMappingMapper.deleteByPrimaryKey(typeuuid);
                //根据typeuuid删除资产负债类别映射附属表的数据
                List<String> typeuuidExList = assetTypeSetting001Mapper.selectTypeUuidExById(typeuuid);
                if (StringUtil.isNotEmpty(typeuuidExList)){
                    for (String str: typeuuidExList) {
                        chbAssetTypeMappingExMapper.deleteByPrimaryKey(str);
                    }
                }
                //执行插入操作
                ChbAssetTypeMapping chbAssetTypeMapping = new ChbAssetTypeMapping();
                List<ChbAssetTypeMappingEx> chbAssetTypeMappingExList = new ArrayList<>();
                if(StringUtil.isNotEmpty(dto)){
                    //资产负债类别映射表
                    typeuuid = IDGenerator.generate();
                    chbAssetTypeMapping.setTypeuuid(typeuuid);
                    chbAssetTypeMapping.setAssetType1(dto.getAssetType1());
                    chbAssetTypeMapping.setAssetType2(dto.getAssetType2());
                    chbAssetTypeMapping.setAssetType3(dto.getAssetType3());
                    chbAssetTypeMapping.setAssetBusiType(dto.getAssetBusiType());
                    flag = chbAssetTypeMappingMapper.insert(chbAssetTypeMapping);
                    //资产负债类别映射附属表
                    chbAssetTypeMappingExList = dto.getSelectedItem1();
                    if (StringUtil.isNotEmpty(chbAssetTypeMappingExList)){
                        for (ChbAssetTypeMappingEx entity: chbAssetTypeMappingExList) {
                            String typeuuidEx = IDGenerator.generate();
                            entity.setTypeuuid(typeuuidEx);
                            entity.setMappingUuid(typeuuid);
                            chbAssetTypeMappingExMapper.insert(entity);
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 删除(全删全插)
     * @param dto
     * @return
     */
    public Integer doDelete(AssetTypeSetting001Dto dto) {
      List<AssetTypeSetting001Dto> dtolist = dto.getSelectedItem();
      for(AssetTypeSetting001Dto dto2:dtolist){
          String typeuuid = dto2.getTypeuuid();
        //删除 资产负债类别映射表
        chbAssetTypeMappingMapper.deleteByPrimaryKey(typeuuid);
        //删除 资产负债类别映射附属表
        List<String> typeuuidExList = assetTypeSetting001Mapper.selectTypeUuidExById(typeuuid);
        if (StringUtil.isNotEmpty(typeuuidExList)) {
            for (String str : typeuuidExList) {
                chbAssetTypeMappingExMapper.deleteByPrimaryKey(str);
            }
        }
      }
        return 1;
    }

    /**
     * 编辑
     * @param typeuuid
     * @return
     */
    public AssetTypeSetting001Dto selectEntityBySearchMap(String typeuuid) {
        AssetTypeSetting001Dto assetTypeSetting001Dto = new AssetTypeSetting001Dto();
        //查询映射表数据
        ChbAssetTypeMapping chbAssetTypeMapping = chbAssetTypeMappingMapper.selectByPrimaryKey(typeuuid);

        assetTypeSetting001Dto.setTypeuuid(chbAssetTypeMapping.getTypeuuid());
        assetTypeSetting001Dto.setAssetType1(chbAssetTypeMapping.getAssetType1());
        assetTypeSetting001Dto.setAssetType2(chbAssetTypeMapping.getAssetType2());
        assetTypeSetting001Dto.setAssetType3(chbAssetTypeMapping.getAssetType3());
        assetTypeSetting001Dto.setAssetBusiType(chbAssetTypeMapping.getAssetBusiType());

        List<ChbAssetTypeMappingEx> chbAssetTypeMappingExList = new ArrayList<>();
        //查询附属表中所有与映射表关联数据的typeuuid
        List<String> typeuuidExList = assetTypeSetting001Mapper.selectTypeUuidExById(typeuuid);
        if (StringUtil.isNotEmpty(typeuuidExList)){
            for (String str: typeuuidExList) {
                //查询上述typeuuid对应数据
                ChbAssetTypeMappingEx chbAssetTypeMappingEx = chbAssetTypeMappingExMapper.selectByPrimaryKey(str);
                chbAssetTypeMappingExList.add(chbAssetTypeMappingEx);
            }
            assetTypeSetting001Dto.setSelectedItem1(chbAssetTypeMappingExList);
        }
        return assetTypeSetting001Dto;
    }

    /**
     * 新建校验
     * @param param
     * @return
     */
    public AssetTypeSetting001Dto isConfilct(Map<String, Object> param) {
        return assetTypeSetting001Mapper.isConfilct(param);
    }

    /**
     * 删除右侧列表数据
     * @param dto
     * @return
     */
    public Integer doExDelete(AssetTypeSetting001Dto dto) {
        String typeuuidEx = dto.getSelectedItem().get(0).getTypeuuid();
        chbAssetTypeMappingExMapper.deleteByPrimaryKey(typeuuidEx);
        return 1;
    }

    /**
     *
     * @param param
     * @return
     */
    public List<AssetTypeSetting001Dto> findExPage(Map<String, Object> param) {
        List<AssetTypeSetting001Dto> dtoList = assetTypeSetting001Mapper.findExPage(param);
        return dtoList;
    }

    @Override
    protected void beforeCreateExcel(List<AssetTypeSetting001Dto> contents) {
        if (StringUtil.endsWith(elementId, "_btn_exportCheck")) {
            Map<String, Object> param = new HashMap<>();
            param.put("list",contents);
            List<AssetTypeSetting001Dto> result = assetTypeSetting001Mapper.selectForExport(param);
            contents.clear();
            contents.addAll(result);
        }
    }

    public Page<AssetTypeSetting001Dto> selectForExport(Map<String, Object> param) {
        List<AssetTypeSetting001Dto> list = assetTypeSetting001Mapper.selectForPage(param);
        param.put("list",list);
        Page<AssetTypeSetting001Dto> page = queryForPage(AssetTypeSetting001Mapper.class,"selectForExport",param);;
        return page;
    }

    /**
     * 导出
     */
    @Override
    protected void buildExportParam(ExportParam exportParam) {
        elementId = exportParam.getSelectParam().get("elementId").toString();
        exportParam.config(
                "/com/joyintech/tams/svreport/chb/assettypesetting/conf/export_conf.xml").dataSource(DataSourceType.MASERT);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Page<AssetTypeSetting001Dto> selectPageData(Map<String, Object> selectParam) {
//        return selectForPage(selectParam);
        return selectForExport(selectParam);
    }

}
