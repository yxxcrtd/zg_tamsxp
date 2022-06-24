package com.joyintech.tams.report.system.systempbccapassetinfo.dao;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.tams.report.system.systempbccapassetinfo.dto.PbcCapAssetInfo001Dto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 人行资金信托代码管理Mapper
 *
 * @author kx
 *
 * @create 2019/12/24
 */
@Repository
public interface PbcCapAssetInfo001DtoMapper {

    int deleteByPrimaryKey(String assetUuid);

    int insert(PbcCapAssetInfo001Dto record);

    int insertSelective(PbcCapAssetInfo001Dto record);

    int insertBasicByBasicInfo(PbcCapAssetInfo001Dto record);

    PbcCapAssetInfo001Dto selectByAssetId(PbcCapAssetInfo001Dto record);

    int updateByPrimaryKeySelective(PbcCapAssetInfo001Dto record);

    int updateByPrimaryKey(PbcCapAssetInfo001Dto record);

    /**
     * 查询列表数据
     *
     * @param param
     * @return
     */
    List<PbcCapAssetInfo001Dto> queryForPage(Map<String, Object> param);

    /**
     * 资产代码下拉框取得值
     *
     * @param param
     * @return
     */
    List<SelectOptionDto> getAssetCodeList(Map<String, Object> param);


    /**
     * 人行资金信托代码下拉框取得值
     *
     * @param param
     * @return
     */
    List<SelectOptionDto> setPobCasSeTidList(Map<String, Object> param);


    /**
     * 导出功能需要导出查询条件的所有内容
     *
     * @param param
     * @return
     */
    List<PbcCapAssetInfo001Dto> querySearchPageList(Map<String, Object> param);


    /**
     * 查询统一资产代码对应的人行资金信托代码是否已存在
     *
     * @param list
     * @param statusEffective
     * @return
     */
    List<String> getPobCassetId(@Param("list") List<PbcCapAssetInfo001Dto> list,
                                @Param("statusEffective") String statusEffective);


    /**
     * 判断Excel文件中资产代码在资产基本信息表中是否存在
     *
     * @param assetId
     * @return
     */
    int statisticalAssetId(String assetId);


    /**
     * 判断导入的资产是否有不属于信托公司资管产品的情况
     *
     * @param assetId
     * @return
     */
    int statisticalCorrectAssetId(String assetId);


    /**
     * 同查询一个人行资金信托代码是否对应了多个资产代码同时去除相同情况
     * @param list
     * @param statusEffective
     * @return
     */
    List<PbcCapAssetInfo001Dto> selectPobcassetIdAndAssetId(@Param("list") List<PbcCapAssetInfo001Dto> list,
                                             @Param("statusEffective") String statusEffective);

    /*根据资产代码查询资产名称*/
    List<PbcCapAssetInfo001Dto> selectAssetInofByAssetId(String assetId);

    /**
     * 查询所有有效数据便于校验
     *
     * @param
     * @return
     */
    List<PbcCapAssetInfo001Dto> selectPobcassetIdList();
}
