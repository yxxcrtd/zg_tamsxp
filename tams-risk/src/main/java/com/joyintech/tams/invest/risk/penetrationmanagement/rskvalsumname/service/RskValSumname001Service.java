/*
 * 文件名：RskValSumnameService001.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年12月17日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.TreeNode;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.dto.RskValSumnameDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author wangzehui
 * @version 1.0 2018年12月17日
 * @see RskValSumnameService001
 * @since 1.0
 */
@Service
public class RskValSumname001Service extends CommonExportService<RskValSumnameDto> {


    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    public List<TreeNode<RskValSumnameDto>> getSumName(Map<String, Object> map) {
        List<TreeNode<RskValSumnameDto>> retList = new ArrayList<>();
        Page<TreeNode<RskValSumnameDto>> page = new Page<>();
        // 获得查询得到的申报附件方案名称
        List<RskValSumnameDto> list = restTemplate.postForList(
                ServiceApplication.RISK_SERVICE_NAME +
                        "/RskValSumname001/getSumName",
                map,RskValSumnameDto.class);
        if (!StringUtil.isEmptyList(list)) {
            // 将查询到的结果赋值树节点
            for (RskValSumnameDto dto : list) {
                TreeNode data = new TreeNode();
                data.setId(dto.getId());
                data.setName(dto.getName());
                data.setObject(dto.getTrimChars());
                retList.add(data);
            }
            page.setRoot(retList);
            page.setRecords(retList.size());
            return retList;
        }
        else {
            return null;
        }
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param param
     * @return
     * @see
     * @since 1.0
     */
    public Page<RskValSumnameDto> selectForPage(Map<String, Object> param) {
        return restTemplate.postForPage(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname001/selectForPage",
            param,RskValSumnameDto.class);
    }
    
    public List<RskValSumnameDto> selectForList(String sumId) {
        return restTemplate.getForList(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname001/selectForList?sumId="+sumId,
            RskValSumnameDto.class);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public boolean getCountById(RskValSumnameDto dto) {
        return restTemplate.postForObject(
            ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname001/getCountById",dto,
            boolean.class);
    }
    /**估值表汇总科目默认映射数
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public boolean getAssetSumInfoIsDefault(RskValSumnameDto dto) {
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME +"/RskValSumname001/getAssetSumInfoIsDefault",dto,
                boolean.class);
    }

}
