/*
 * 文件名：RskValAssetCode003Service.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月17日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.base.entity.RskValtblSubject;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.common.risktrial.dto.TreeNode;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.dto.PriviewParamDto;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.dto.RskValtblAssetSubMapDtlDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  资产科目映射明细
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月17日
 * @see RskValAssetCode003Service
 * @since 1.0
 */
@Service
public class RskValAssetCode003Service extends BaseService<RskValtblAssetSubMapDtlDto>{

    /**
     * 解析源科目代码
     * <一句话功能简述>
     * <功能详细描述>
     * @param list
     * @return
     * @see
     * @since 1.0
     */
    
    public List<TreeNode> getAllTreeSourceNode(List<List<String>> list, PriviewParamDto dto) {
        List<TreeNode> tree_List = new ArrayList<>();
        List<List<String>> ls_new = new ArrayList<List<String>>();
        String[] arr = SysDataUtil.getNameFromDataDict(DictCode.科目代码级别类型, dto.getAssetCodeType()).split(",");
        String parent_assetCode = StringUtil.EMPTY;
        if(null!=list&&!list.isEmpty()){
          //排除汇总行  第一个为数字
            for(int i =0;i<list.size();i++){
                if(StringUtil.isNotEmpty(list.get(i).get(dto.getAssetCodeColIndex()-1))){
                    Pattern pattern = Pattern.compile("[0-9]*"); 
                       Matcher isNum = pattern.matcher(list.get(i).get(dto.getAssetCodeColIndex()-1).substring(0,1));
                       if(isNum.matches()){
                           ls_new.add(list.get(i));
                           continue;
                       }
                       break;
                }
            }
            list.clear();list.addAll(ls_new);
            for(int m=0;m<list.size();m++){
                TreeNode treeNode = new TreeNode();
                String assetCode = list.get(m).get(dto.getAssetCodeColIndex()-1).replaceAll(" ", "").replace(".","").replace("-","");
                String assetName = assetCode+"("+list.get(m).get(dto.getAssetCodeColIndex())+")";
                treeNode.setId(assetCode);treeNode.setName(assetName);
                treeNode.setOpen(true);
                for(int i=0;i<arr.length;i++){
                    if(assetCode.length()==Integer.parseInt(arr[i])){
                        if(i>0){
                            parent_assetCode = assetCode.substring(0,Integer.parseInt(arr[i-1]));
                            for(int n=0;n<list.size();n++){
                                if(StringUtil.equals(parent_assetCode,list.get(n).get(dto.getAssetCodeColIndex()-1))){
                                    treeNode.setpId(parent_assetCode);
                                    break;
                                }
                            }
                        }
                        tree_List.add(treeNode);
                    }
                }
            }
        }
        return tree_List;
    }
    
    /**
     * 获取目标科目代码
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public List<TreeNode> getAllTreeTargetNode(){
        List<TreeNode> tree_List = new ArrayList<TreeNode>();
        //目标科目代码固定长度
        int[] arr = {4,6,8}; String parent_assetCode = StringUtil.EMPTY;
        List<RskValtblSubject> rskValtblSubjectCodeList = selecctRskValtblSubjectCodeList("01");
        if(null!=rskValtblSubjectCodeList&&!rskValtblSubjectCodeList.isEmpty()){
            for(int m=0;m<rskValtblSubjectCodeList.size();m++){
                TreeNode treeNode = new TreeNode();
                String assetCode = rskValtblSubjectCodeList.get(m).getSubCode();
                // 特殊处理100201科目 & 120401科目
                if ((assetCode.startsWith("100201") && !StringUtil.equals("100201",assetCode))||(assetCode.startsWith("120401") && !StringUtil.equals("120401",assetCode))) {
                   continue;
                }
                String assetName = assetCode+"("+rskValtblSubjectCodeList.get(m).getSubName()+")";
                treeNode.setId(assetCode);treeNode.setName(assetName);
                treeNode.setOpen(true);
                for(int i=0;i<arr.length;i++){
                    if(rskValtblSubjectCodeList.get(m).getSubCode().length()==arr[i]){
                        if(i>0){
                            parent_assetCode = assetCode.substring(0,arr[i-1]);
                            for(int n=0;n<rskValtblSubjectCodeList.size();n++){
                                if(StringUtil.equals(parent_assetCode,rskValtblSubjectCodeList.get(n).getSubCode())){
                                    treeNode.setpId(parent_assetCode);
                                    break;
                                }
                            }
                        }
                        tree_List.add(treeNode);
                    }
                }
            }
        }
        return tree_List;
    }
    /**
     * 查询目标科目代码
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public  List<RskValtblSubject> selecctRskValtblSubjectCodeList(String subType){
        return restTemplate.getForList(ServiceApplication.RISK_SERVICE_NAME
            + "/RskValAssetCode/selecctRskValtblSubjectCodeList?subType="+subType,
            RskValtblSubject.class);
    }
    /**
     * 查询目标汇总科目代码
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public  Page<SelectOptionDto> selecctRskValtblSubjectSumCodeList(Map<String, Object> pageParam){
        return restTemplate.postForPage(ServiceApplication.RISK_SERVICE_NAME
                        + "/RskValAssetCode/selecctRskValtblSubjectSumCodeList",pageParam,
                SelectOptionDto.class);
    }
    /**
     * 查询目标科目代码
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public  List<String> selecctRskValtblSubCodeList(String subType){
        return  restTemplate.getForObject(ServiceApplication.RISK_SERVICE_NAME
                        + "/RskValAssetCode/selecctRskValtblSubCodeList?subType="+subType,
                List.class);
    }
}
