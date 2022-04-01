package com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.select.TreeNode;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioDetailDto;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 对账参数设置
 */
@Service
public class MultiPortfolioMaintenance002Service extends CommonExportService<MutiptlPortfolioDetailDto>{




    public List<TreeNode> getMutiptlPortfolioAcctnoTree(Map<String,Object> parmMap) {
        List<TreeNode> treeProList = restTemplate.postForList(
                ServiceApplication.BASICDATA_SERVICE_NAME + "/MultiPortfolioMaintenance002/getMutiptlPortfolioAcctnoTree",
                parmMap,TreeNode.class);
        return treeProList;
    }

    /**
     * 完成增删改
     * @param entity 业务实体
     */
    public void isnertOrDelete(MutiptlPortfolioDetailDto entity) {
        String action = StringUtil.EMPTY;
        // 删除
        if (entity.getOperateType().equals("DELETE")) {
            action = "delete";
            // 插入
        } else if (entity.getOperateType().equals("NEW")) {
            action = "insert";
        }
        restTemplate.postForObject(ServiceApplication.BASICDATA_SERVICE_NAME + "/MultiPortfolioMaintenance002/" + action, entity, Integer.class);
    }
    public AjaxResult check(Object value) {
        AjaxResult ajaxResult=new AjaxResult();
        List<ValidMessage> errorList = new ArrayList<ValidMessage>();
        MutiptlPortfolioDetailDto dto = (MutiptlPortfolioDetailDto) value;
        if (StringUtil.equals(dto.getOperateType(), "confirm")||StringUtil.equals(dto.getOperateType(), "NEW")) {
            for(MutiptlPortfolioDetailDto item:dto.getSelectedItem()){
                if(isExistPortfolio(item)>0){
                    errorList.add(new ValidMessage(getMessage("MultiPortfolioMaintenance002.isExistPortfolio",item.getPortfolioId()+":"+item.getAcctNo())));
                    break;
                }
            }
        }else if (StringUtil.equals(dto.getOperateType(), "NEW")) {
            for(MutiptlPortfolioDetailDto item:dto.getSelectedItem()){
                if(isExistPortfolio(item)>0){
                    errorList.add(new ValidMessage(getMessage("MultiPortfolioMaintenance002.isExistPortfolio",item.getPortfolioId()+":"+item.getAcctNo())));
                    break;
                }
            }
        }
        ajaxResult.setErrorList(errorList);
        return ajaxResult;
    }
    /**
     * 选择记录（组合代码+资金账户+生效日）已在该多组合中存在时
     * @param dto 业务实体
     */
    public Integer isExistPortfolio(MutiptlPortfolioDetailDto dto) {
        Integer count = restTemplate.postForObject(
                ServiceApplication.BASICDATA_SERVICE_NAME + "/MultiPortfolioMaintenance002/isExistPortfolio",
                dto,Integer.class);
        return count;
    }
}
