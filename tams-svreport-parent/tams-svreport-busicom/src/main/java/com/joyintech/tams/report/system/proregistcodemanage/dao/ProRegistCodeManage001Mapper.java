/*
 * 文件名：ProRegistCodeManage001Mapper.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：tangliang
 * 修改时间：2017年9月20日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.system.proregistcodemanage.dao;

import java.util.List;
import java.util.Map;

import com.joyintech.tams.svreport.interf.chb.proddeclare.dto.ChbPrdDeclareInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.tams.common.base.entity.ChbPrdBaseinfo;
import com.joyintech.tams.report.system.proregistcodemanage.dto.ProRegistCodeManage001Dto;
import com.joyintech.tams.report.system.proregistcodemanage.dto.ProRegistCodeManage002Dto;

/**
 * 产品登记编码管理mapper文件
 * 〈功能详细描述〉
 * @author tangliang
 * @version 1.0 2017年9月20日
 * @see ProRegistCodeManage001Mapper
 * @since 1.0
 */
@Repository
public interface ProRegistCodeManage001Mapper {

    /**
     * 产品登记编码列表查询
     * <p>根据指定条件查询列表数据</p>
     * @param param 查询参数
     * @return 查询结果列表
     */
    List<ProRegistCodeManage001Dto> queryForPage(Map<String, Object> param);
    List<ProRegistCodeManage001Dto> queryForPageOld(Map<String, Object> param);
    

    /**
     * 画面中的产品下拉框数据获取
     * <p>从数据库中查询出产品名称和产品代码，并以列表返回</p>
     * @param param 查询参数
     * @return 查询结果
     */
    List<SelectOptionDto> queryProdList(Map<String, Object> param);
    /**
     * 产品登记编码查询
     * 根据指定条件查询列表数据
     * @return 查询结果列表
     */
    List<ProRegistCodeManage001Dto> queryRegCode();
    /**
     * 产品代码查询
     * <p>根据指定条件查询列表数据</p>
     * @param regCode 查询参数
     * @return 查询结果列表
     */
    List<ProRegistCodeManage001Dto> queryProdid(String regCode);

    /**
     * 统计产品代码数量
     *
     * @param map 条件
     * @return 数量
     * @see
     * @since 1.0
     */
    int isExist(Map<String, Object> map);

    /**
     * 获取产品登记代码下拉菜单
     * @param pageParam 查询参数
     * @return dto数据
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> queryPrdRegList(Map<String, Object> pageParam);
    /**
     * 查询登记编码是否重复
     * @param list 产品list
     * @param orgCode 机构代码
     * @return list
     * @see
     * @since 1.0
     */
    List<String> getRegistExcelList(@Param("list") List<ProRegistCodeManage001Dto> list,
                                                       @Param("orgCode")  String orgCode);

    /**
     * 校验产品代码是否已经被使用
     * @param map 产品代码
     * @return 结果条数
     * @see
     * @since 1.0
     */
    Integer selectProdIsUsed(Map<String, Object> map);

    /**
     * 查询产品代码和产品登记编码的对应关系
     * @return
     * @see
     * @since 1.0
     */
    List<Map<String, String>> selectProdInfo();
    
    /**
     * 查询产品登记代码
     * @return
     */
    List<SelectOptionDto> queryAllRegistCode();
    
    List<Map<String, String>> queryRegistCodeByProdId(@Param("prodid") String prodid);

    List<ChbPrdBaseinfo> getProdinfo(Map<String, Object> param);
    
    List<ChbPrdBaseinfo> getProdinfoByInsId(Map<String, Object> param);
    
    int updateOperateModeByProdId(@Param("list")List<ProRegistCodeManage001Dto>list);
    
    ProRegistCodeManage002Dto selectInfo(String prodId);
    ProRegistCodeManage002Dto selectInfoN(String prodId);
    
    Map<String, Object> selectId(Map<String, Object> map);
    
    Map<String, Object> selectCode(Map<String, Object> map);
    ChbPrdDeclareInfoDto selectInfoByProdId(String prodId);

    List<Map<String, String>> selectProdBaseInfo();

    int checkRegCode(String regCode);

    int selectRegCode(@Param("prodInsId")String prodInsId,@Param("regCode")String regCode);
}
