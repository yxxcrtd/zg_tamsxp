package com.joyintech.tams.report.submitmaintenance.agentprdinfo.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.submitmaintenance.agentinstitution.dto.AgentInstitutionDto;
import com.joyintech.tams.report.submitmaintenance.agentprdinfo.dao.ChbAgentPrdInfoMapper;
import com.joyintech.tams.report.submitmaintenance.agentprdinfo.dto.AgentPrdInfoDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("AgentPrdInfo001Service")

public class AgentPrdInfo001Service extends CommonExportService<AgentPrdInfoDto> {
    @Autowired
    private ChbAgentPrdInfoMapper chbAgentPrdInfoMapper;
    /**
     * 当在做导入前的check就是判断业务数据是否需要覆盖的时候在webresult的data中塞入这个值可以不会弹框两次
     */
    private static final String NO_INFO_SHOWOUT = "noInfoShowOut";

    /**
     * 分页查询
     */
    public Page<AgentPrdInfoDto> selectForPage(int page, int length, Map<String, Object> pageParam)
    {
        return super.queryForPage(page,length,() -> chbAgentPrdInfoMapper.selectForPage(pageParam));
    }

    /**
     * 导出查询
     * @param params
     * @return
     * @throws Exception
     */
    public List<AgentPrdInfoDto> queryAllList(Map<String, Object> params) throws Exception{
        return chbAgentPrdInfoMapper.selectForPage(params);
    }

    /**
     * 逻辑删除
     */
    public void deleteByPrimaryKey(AgentPrdInfoDto dto){
        List<AgentPrdInfoDto> dtolist = dto.getSelectedItem();
        if (!dtolist.isEmpty() && dtolist.size() != 0) {
            for (int i = 0 ; i < dtolist.size() ; i++) {
                String agentInstitutionCode = dtolist.get(i).getAgentInstitutionCode();
                String prodRegistCode = dtolist.get(i).getProdRegistCode();
                //info表内容逻辑删除
                chbAgentPrdInfoMapper.deleteInfo(agentInstitutionCode,prodRegistCode);
            }
        }
    }

    /**
     * 导入文件之前检查文件 导入的excel是否有重复数据
     *
     * param fileName 导入的文件名名
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public AjaxResult checkConfirmMsgForImportData(List<List<String>> dataList) {
        AjaxResult webResult = new AjaxResult();
        List<ValidMessage> errorList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++ ) {
            for (int j = i+1; j < dataList.size(); j++ ) {
                if(StringUtils.equals(dataList.get(i).get(1), dataList.get(j).get(1)) && StringUtils.equals(dataList.get(i).get(2), dataList.get(j).get(2)) ){
                    ValidMessage validMessage = new ValidMessage("第"+(i+2)+"与"+"第"+(j+2)+"代理销售机构代码相等时，产品登记编码不能重复");
                    errorList.add(validMessage);
                }
            }
        }
        if(errorList.size() == 0){
            webResult.setData(NO_INFO_SHOWOUT);
        }else{
            webResult.setErrorList(errorList);
        }
        return webResult;
    }

    /**
     * 导入文件
     * <p>
     * 解析excel文件并进行验证
     * </p>
     * @return 导入结果
     */
    public List<ExcelValidDto> importData(List<List<String>> dataList, List<List<String>> tempList, String userName, String orgCode, String deptCode) {
        if (!StringUtil.isEmptyList(dataList)) {
            List<String> headList = new ArrayList<String>();
            headList.add("发行机构代码");
            headList.add("代理销售机构代码");
            headList.add("产品登记编码");
            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }
            if (null != dataList) {
                List<AgentPrdInfoDto> entities = new ArrayList<>(dataList.size());
                List<ExcelValidDto> checkResult= checkExcelSales(dataList, entities,userName,deptCode);
                //校验是否已存在逻辑删除的数据
                List<AgentPrdInfoDto> AgentInstitutionCodeDImportList =  chbAgentPrdInfoMapper.checkAgentInstitutionCodeDImport();
                Map<String,AgentPrdInfoDto> AgentInstitutionCodeDImportMap = new HashMap<String,AgentPrdInfoDto>();
                for(AgentPrdInfoDto info :AgentInstitutionCodeDImportList){
                    String key = info.getAgentInstitutionCode() + info.getProdRegistCode();
                    AgentInstitutionCodeDImportMap.put(key,info);
                }
                if (checkResult.isEmpty()) {
                    entities = convertToEntity(dataList, orgCode);
                    for(AgentPrdInfoDto dto : entities){
                        //check表中是否存在相同的逻辑删除数据 如果存在就先删除再插入
                        String key = dto.getAgentInstitutionCode() + dto.getProdRegistCode();
                        if(AgentInstitutionCodeDImportMap.get(key) != null){
                            chbAgentPrdInfoMapper.delete(dto.getAgentInstitutionCode(),dto.getProdRegistCode());
                        }
                        chbAgentPrdInfoMapper.insert(dto);
                    }
                }
                return checkResult;
            }
        }else{
            List<ExcelValidDto> checkResult = new ArrayList<>();
            ExcelValidDto excelValidDto = new ExcelValidDto();
            String errorMsg = "模板为空，请输入数据！";
            excelValidDto.setErrorInfo(errorMsg);
            checkResult.add(excelValidDto);
            return checkResult;
        }
        return null;
    }

    /**
     * 导入数据check
     * @param dataList
     * @param entities
     * @param userName
     * @param deptCode
     * @return
     */
    private List<ExcelValidDto> checkExcelSales(List<List<String>> dataList, List<AgentPrdInfoDto> entities, String userName, String deptCode) {
        String orgCode = CommonUtil.getOrgCode();
        String issuePartyId = chbAgentPrdInfoMapper.selectIssuePartyId(orgCode);
        //代理销售机构代码
        List<AgentPrdInfoDto> agentInstitutionCodeList = chbAgentPrdInfoMapper.selectAgentInstitutionCodeList();
        Map<String,String> agentInstitutionCodeMap = new HashMap<String,String>();
        for(int i = 0;i < agentInstitutionCodeList.size();i++){
            agentInstitutionCodeMap.put(agentInstitutionCodeList.get(i).getAgentInstitutionCode(),agentInstitutionCodeList.get(i).getAgentInstitutionCode());
        }

        //产品登记编码
        List<AgentPrdInfoDto> prodRegistCodeList = chbAgentPrdInfoMapper.selectProdRegistCodeList();
        Map<String,String> prodRegistCodeMap = new HashMap<String,String>();
        for(int i = 0;i < prodRegistCodeList.size();i++){
            prodRegistCodeMap.put(prodRegistCodeList.get(i).getProdRegistCode(),prodRegistCodeList.get(i).getProdRegistCode());
        }

        //check代销代码相同时产品登记编码不能相同
        List<AgentPrdInfoDto> ProdRegistCodeImportList = chbAgentPrdInfoMapper.checkProdRegistCodeImport();
        Map<String,String> ProdRegistCodeImportMap = new HashMap<String,String>();
        for(AgentPrdInfoDto info:ProdRegistCodeImportList){
            String key = info.getAgentInstitutionCode() + info.getProdRegistCode();
            ProdRegistCodeImportMap.put(key,key);
        }

        List<ExcelValidDto> checkResult = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++ ) {
            //是否正常标志
            boolean flag = true;
            List<String> list = dataList.get(i);

            Map<String,Object> param = new HashMap<String,Object>();
            List<String> message = new ArrayList<>();
            //发行机构代码
            if(list.get(0) == null || list.get(0).trim().equals("")){
                flag = false;
                message.add("发行机构代码不能为空");
            }else if(list.get(0) != null && !list.get(0).trim().equals("")){
                if(!issuePartyId.equals(list.get(0))){
                    flag = false;
                    message.add("发行机构代码不是本行机构代码");
                }
            }
            //代理销售机构代码
            if(list.get(1) == null || list.get(1).trim().equals("")){
                flag = false;
                message.add("代理销售机构代码不能为空");
            }else if(list.get(1) != null && !list.get(1).trim().equals("")){
                if(agentInstitutionCodeMap.get(list.get(1)) == null){
                    flag = false;
                    message.add("代理销售机构代码未被维护");
                }
            }
            //产品登记编码
            if(list.get(2) == null || list.get(2).trim().equals("")){
                flag = false;
                message.add("产品登记编码不能为空");
            }else if(list.get(2) != null && !list.get(2).trim().equals("")){
                if(prodRegistCodeMap.get(list.get(2)) == null){
                    flag = false;
                    message.add("产品登记编码未被维护");
                }
            }
            //代理销售机构代码相同时，产品登记编码不可重复
            if((list.get(1) != null && !list.get(1).trim().equals("")) && (list.get(2) != null && !list.get(2).trim().equals(""))){
                String key = list.get(1) + list.get(2);
                if(ProdRegistCodeImportMap.get(key) != null){
                    flag = false;
                    message.add("代理销售机构代码相同时，产品登记编码不可重复");
                }
            }
            if (CollectionUtils.isNotEmpty(message)) {
                checkResult.add(new ExcelValidDto(list.get(2), String.valueOf(i+2),
                        StringUtil.joinString(message, GlobalConstant.SEMICOLON)));
            }
        }
        return checkResult;
    }

    /**
     * excel数据转为java对象
     * <p> 将excel导入的数据，按行转为ProRegistCodeManage001Dto  FinProductValuation对象，同时验证各字段值</p>
     * @param dataList excel导入的数据
     * @return 验证信息
     */
    private List<AgentPrdInfoDto> convertToEntity(List<List<String>> dataList,String orgCode) {
        //产品代码及名称
        List<AgentPrdInfoDto> prdCodeList = chbAgentPrdInfoMapper.selectPrdCodeList();
        Map<String,AgentPrdInfoDto> prdCodeMap = new HashMap<String,AgentPrdInfoDto>();
        for(int i = 0;i < prdCodeList.size();i++){
            prdCodeMap.put(prdCodeList.get(i).getProdRegistCode(),prdCodeList.get(i));
        }
        // 返回的list定义
        List<AgentPrdInfoDto> returnInfoList = new ArrayList<AgentPrdInfoDto>();
        AgentPrdInfoDto AgentPrdInfoDto = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> list = dataList.get(i);
            AgentPrdInfoDto = new AgentPrdInfoDto();
            //发行机构代码
            AgentPrdInfoDto.setIssuePartyId(list.get(0));

            //代理销售机构代码
            AgentPrdInfoDto.setAgentInstitutionCode(list.get(1));

            //产品登记编码
            AgentPrdInfoDto.setProdRegistCode(list.get(2));

            //产品代码
            AgentPrdInfoDto.setProdId(prdCodeMap.get(list.get(2)).getProdId());

            //产品名称
            AgentPrdInfoDto.setProdName(prdCodeMap.get(list.get(2)).getProdName());

            //数据状态
            AgentPrdInfoDto.setStatus("E");
            AgentPrdInfoDto.setCreateDept(CommonUtil.getDeptCode());
            AgentPrdInfoDto.setCreateDept(CommonUtil.getOrgCode());
            returnInfoList.add(AgentPrdInfoDto);
        }
        return returnInfoList;
    }


    /**
     * 代理销售机构代码下拉框
     */
    public List<SuggestDto> getAgentInstitutionCode(Map<String, Object> map) {
        return chbAgentPrdInfoMapper.getAgentInstitutionCode(map);
    }

    /**
     * 产品代码下拉框
     */
    public List<SuggestDto> getProdId(Map<String, Object> map) {
        return chbAgentPrdInfoMapper.getProdId(map);
    }

    /**
     * 产品登记编码下拉框
     */
    public List<SuggestDto> getProdRegistCode(Map<String, Object> map) {
        return chbAgentPrdInfoMapper.getProdRegistCode(map);
    }

}
