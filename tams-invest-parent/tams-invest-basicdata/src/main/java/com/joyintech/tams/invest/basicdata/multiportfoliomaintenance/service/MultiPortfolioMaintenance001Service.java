package com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.select.TreeNode;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioDetailDto;
import com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioTreeDto;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 对账参数设置
 */
@Service
public class MultiPortfolioMaintenance001Service extends CommonExportService<MutiptlPortfolioTreeDto>{

    /**
     * 分页查询
     *
     * @param pageParam 画面参数
     * @return 分页数据
     */
    public Page<MutiptlPortfolioDetailDto> findPage(Map<String, Object> pageParam) {
        // 通过微服务调用查询分页数据
        return restTemplate.postForPage(ServiceApplication.BASICDATA_SERVICE_NAME + "/MultiPortfolioMaintenance001/selectForPage",
                pageParam, // 画面参数
                MutiptlPortfolioDetailDto.class);
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<TreeNode> getMutiptlPortfolioTree(Map<String,Object> parmMap) {
            List<TreeNode> treeProList = restTemplate.postForList(
                    ServiceApplication.BASICDATA_SERVICE_NAME + "/MultiPortfolioMaintenance001/getMutiptlPortfolioTree",
                    parmMap,TreeNode.class);
            treeProList.forEach(treeNode -> {
                treeNode.setOpen(true);
            });
            return treeProList;
    }

    /**
     * 增加父节点
     * <一句话功能简述>
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult addParentNode(Map<String, Object> map){
        String treeName=(String)map.get("treeName");
        if("".equals(treeName)){
            return new AjaxResult("必填项不能为空", MessageType.错误消息);
        }
        Map<String, Object> keymap = new HashMap<String,Object>();
        keymap.put("treeName", treeName);
        MutiptlPortfolioDetailDto dto =restTemplate.postForObject(ServiceApplication.BASICDATA_SERVICE_NAME +
                        "/MultiPortfolioMaintenance001/selectByKey",keymap,
                MutiptlPortfolioDetailDto.class);
        if(dto!=null){
            return new AjaxResult("该数据已经存在", MessageType.错误消息);
        }
        String four= UUID.randomUUID().toString().replace("-", "").substring(0, 3);
        map.put("treeId", "DZHF"+DateUtil.format(DateUtil.now(),DateUtil.DEFAULT_OTHER_FORMAT)+four);//DZHF+8位日期+4位自动采番
        map.put("createUser", LoginUserUtil.getUserName());
        map.put("updateUser",LoginUserUtil.getUserName());
        map.put("createDept", LoginUserUtil.getDeptCode());
        String date= DateUtil.getCurrentDate();
        map.put("createTime",date);
        map.put("updateTime",date);
        restTemplate.postForObject(
                ServiceApplication.BASICDATA_SERVICE_NAME + "/MultiPortfolioMaintenance003/addParentNode",
                map, Integer.class);
        return null;
    }

    /**
     * 增加子节点
     * <一句话功能简述>
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult addSonNode(Map<String, Object> map){
        String treeName=(String)map.get("treeName");
        if("".equals(treeName)){
            return new AjaxResult("必填项不能为空", MessageType.错误消息);
        }
        Map<String, Object> keymap = new HashMap<String,Object>();
        keymap.put("treeName", treeName);
        MutiptlPortfolioTreeDto dto =restTemplate.postForObject(ServiceApplication.BASICDATA_SERVICE_NAME +
                        "/MultiPortfolioMaintenance001/selectByKey",keymap,
                MutiptlPortfolioTreeDto.class);
        if(dto!=null){
            return new AjaxResult("该数据已经存在", MessageType.错误消息);
        }
        String four= UUID.randomUUID().toString().replace("-", "").substring(0, 3);
        map.put("treeId", "DZHF"+DateUtil.format(DateUtil.now(),DateUtil.DEFAULT_OTHER_FORMAT)+four);//DZHF+8位日期+4位自动采番        map.put("createUser", LoginUserUtil.getUserName());
        map.put("createUser", LoginUserUtil.getUserName());
        map.put("updateUser",LoginUserUtil.getUserName());
        map.put("createDept", LoginUserUtil.getDeptCode());
        String date= DateUtil.getCurrentDate();
        map.put("createTime",date);
        map.put("updateTime",date);
        restTemplate.postForObject(ServiceApplication.BASICDATA_SERVICE_NAME +
                        "/MultiPortfolioMaintenance004/addSonNode",map,
                Integer.class);

        return null;
    }

    /**
     * 修改方法
     * <一句话功能简述>
     * <功能详细描述>
     * @param map
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public void getEdit(Map<String, Object> map){
        String treeId=(String)map.get("treeId");
        if(StringUtil.isEmpty(treeId)){
            addParentNode(map);
        }else{
            map.put("updateUser",LoginUserUtil.getUserName());
            map.put("updateTime",DateUtil.getCurrentDate());
            restTemplate.postForObject(ServiceApplication.BASICDATA_SERVICE_NAME + "/MultiPortfolioMaintenance005/getEdit",
                    map,
                    Integer.class);
        }

    }
    /**
     * 删除方法
     * <一句话功能简述>
     * <功能详细描述>
     * @param treeId
     * @param parTreeId
     * @return
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult getDelete(String treeId){
        restTemplate.getForObject(
                ServiceApplication.BASICDATA_SERVICE_NAME + "/MultiPortfolioMaintenance001/getDelete?treeId="+treeId,
                Integer.class);
        return null;
    }
}
