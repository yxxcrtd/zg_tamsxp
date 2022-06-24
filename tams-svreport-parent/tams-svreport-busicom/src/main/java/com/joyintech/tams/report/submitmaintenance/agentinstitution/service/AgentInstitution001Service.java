package com.joyintech.tams.report.submitmaintenance.agentinstitution.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.base.dto.UploadFile;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.system.common.listener.UploadListener;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.submitmaintenance.agentinstitution.dao.ChbAgentInstitutionInfoMapper;
import com.joyintech.tams.report.submitmaintenance.agentinstitution.dto.AgentInstitutionDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;


@Service("AgentInstitution001Service")
public class AgentInstitution001Service extends CommonExportService<AgentInstitutionDto> {
    @Autowired
    private ChbAgentInstitutionInfoMapper chbAgentInstitutionInfoMapper;
    @Autowired
    private UploadListener uploadListener;
    /**
     * 当在做导入前的check就是判断业务数据是否需要覆盖的时候在webresult的data中塞入这个值可以不会弹框两次
     */
    private static final String NO_INFO_SHOWOUT = "noInfoShowOut";

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 分页查询
     */
    public Page<AgentInstitutionDto> selectForPage(int page, int length, Map<String, Object> pageParam)
    {
        return super.queryForPage(page,length,() -> chbAgentInstitutionInfoMapper.selectForPage(pageParam));
    }

    /**
     * 导出查询
     * @param params
     * @return
     * @throws Exception
     */
    public List<AgentInstitutionDto> queryAllList(Map<String, Object> params) throws Exception{
        return chbAgentInstitutionInfoMapper.selectForPage(params);
    }

    /**
     * 逻辑删除
     */
    public void deleteByPrimaryKey(AgentInstitutionDto dto){
        List<AgentInstitutionDto> dtolist = dto.getSelectedItem();
        if (!dtolist.isEmpty() && dtolist.size() != 0) {
            for (int i = 0 ; i < dtolist.size() ; i++) {
                String agentInstitutionCode = dtolist.get(i).getAgentInstitutionCode();
                    //代理销售机构信息表内容逻辑删除
                 chbAgentInstitutionInfoMapper.deleteInfo(agentInstitutionCode);
                //委托销售产品编码表内容逻辑删除
                chbAgentInstitutionInfoMapper.deletePrdInfo(agentInstitutionCode);
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
                if(StringUtils.equals(dataList.get(i).get(1), dataList.get(j).get(1))){
                    ValidMessage validMessage = new ValidMessage("第"+(i+2)+"代理销售机构代码与"+"第"+(j+2)+"代理销售机构代码重复");
                    errorList.add(validMessage);
                }
                if(StringUtils.equals(dataList.get(i).get(2), dataList.get(j).get(2))){
                    ValidMessage validMessage = new ValidMessage("第"+(i+2)+"代理销售机构名称与"+"第"+(j+2)+"代理销售机构名称重复");
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
            headList.add("代理销售机构名称");
            headList.add("备注");
            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }
            if (null != dataList) {
                List<AgentInstitutionDto> entities = new ArrayList<>(dataList.size());
                List<ExcelValidDto> checkResult= checkExcelSales(dataList, entities,userName,deptCode);
                if (checkResult.isEmpty()) {
                    entities = convertToEntity(dataList, orgCode);
                    //校验是否已存在逻辑删除的数据
                    List<AgentInstitutionDto> AgentInstitutionCodeDImportList =  chbAgentInstitutionInfoMapper.checkAgentInstitutionCodeDImport();
                    Map<String,AgentInstitutionDto> AgentInstitutionCodeDImportMap = new HashMap<String,AgentInstitutionDto>();
                    for(AgentInstitutionDto info :AgentInstitutionCodeDImportList){
                        AgentInstitutionCodeDImportMap.put(info.getAgentInstitutionCode(),info);
                    }
                    for(AgentInstitutionDto dto : entities){
                        //check表中是否存在已逻辑删除的数据
                        if(AgentInstitutionCodeDImportMap.get(dto.getAgentInstitutionCode()) != null){
                            //如果存在就先 删除表中删除状态数据再 新增插入
                            chbAgentInstitutionInfoMapper.delete(dto.getAgentInstitutionCode());
                        }
                        chbAgentInstitutionInfoMapper.insert(dto);
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
    private List<ExcelValidDto> checkExcelSales(List<List<String>> dataList, List<AgentInstitutionDto> entities, String userName, String deptCode) {
        String orgCode = CommonUtil.getOrgCode();
        String issuePartyId = chbAgentInstitutionInfoMapper.selectIssuePartyId(orgCode);
        //导入代理销售机构代码重复校验
        List<AgentInstitutionDto> AgentInstitutionCodeList = chbAgentInstitutionInfoMapper.checkAgentInstitutionCodeImport();
        Map<String,AgentInstitutionDto> AgentInstitutionCodeMap = new HashMap<String,AgentInstitutionDto>();
        Map<String,AgentInstitutionDto> AgentInstitutionNameMap = new HashMap<String,AgentInstitutionDto>();
        for(AgentInstitutionDto info :AgentInstitutionCodeList){
            AgentInstitutionCodeMap.put(info.getAgentInstitutionCode(),info);
            AgentInstitutionNameMap.put(info.getAgentInstitutionName(),info);
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
                String regex = "^([CZ][0-9]{5})$";
                if(!list.get(1).matches(regex)){
                    flag = false;
                    message.add("代理销售机构代码格式不对");
                }else if(AgentInstitutionCodeMap.get(list.get(1)) != null){
                    flag = false;
                    message.add("代理销售机构代码已存在");
                }
            }
            //代理销售机构名称
            if(list.get(2) == null || list.get(2).trim().equals("")){
                flag = false;
                message.add("代理销售机构名称不能为空");
            }else if(AgentInstitutionNameMap.get(list.get(2)) != null){
                flag = false;
                message.add("代理销售机构名称已存在");
            }
            if (CollectionUtils.isNotEmpty(message)) {
                checkResult.add(new ExcelValidDto(list.get(1), String.valueOf(i+2),
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
    private List<AgentInstitutionDto> convertToEntity(List<List<String>> dataList,String orgCode) {
        // 返回的list定义
        List<AgentInstitutionDto> returnInfoList = new ArrayList<AgentInstitutionDto>();
        AgentInstitutionDto agentInstitutionDto = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> list = dataList.get(i);
            agentInstitutionDto = new AgentInstitutionDto();
            //发行机构代码
            agentInstitutionDto.setIssuePartyId(list.get(0));

            //代理销售机构代码
            agentInstitutionDto.setAgentInstitutionCode(list.get(1));

            //代理销售机构名称
            if(list.get(2) != null && list.get(2) != "") {
                agentInstitutionDto.setAgentInstitutionName(list.get(2));
            }

            //备注
            if(list.get(3) != null && list.get(3) != "") {
                agentInstitutionDto.setRemark(list.get(3));
            }

            //数据状态
            agentInstitutionDto.setStatus("E");
            agentInstitutionDto.setCreateDept(CommonUtil.getDeptCode());
            agentInstitutionDto.setCreateDept(CommonUtil.getOrgCode());
            returnInfoList.add(agentInstitutionDto);
        }
        return returnInfoList;
    }


    /**
     * 代理销售机构代码下拉框
     */
    public List<SuggestDto> getAgentInstitutionCode(Map<String, Object> map) {
        return chbAgentInstitutionInfoMapper.getAgentInstitutionCode(map);
    }

    //删除附件
    public void deltFile(Map<String, Object> map) {
        String agentInstitutionCode=(String) map.get("agentInstitutionCode");
        String fileNo=(String) map.get("fileNo");
        UploadFile file=uploadListener.fetch(fileNo);
        uploadListener.remove(fileNo);
        if(file!=null && file.getTargetFile().exists()){
            file.getTargetFile().delete();
        }
        AgentInstitutionDto agentInstitutionDto=new AgentInstitutionDto();
        agentInstitutionDto.setAgentInstitutionCode(agentInstitutionCode);
        agentInstitutionDto.setFileNo("");
        agentInstitutionDto.setEnclosureNum("0");
        chbAgentInstitutionInfoMapper.updateEnclosureNum(agentInstitutionDto);
    }

    /**
     * 附件查看
     * @param map
     * @return
     * @throws IOException
     */
    public String getFileList(Map<String, Object> map) throws IOException {
        StringBuffer res=new StringBuffer();
        if(map.get("fileNo")==null){
            return URLEncoder.encode("附件不存在", "UTF-8").replace("+","%20");
        }
        String fileNo=(String) map.get("fileNo");

        UploadFile file=uploadListener.fetch(fileNo);
        if(file==null||file.getTargetFile()==null){
            return URLEncoder.encode("附件丢失", "UTF-8").replace("+","%20");
        }
        res.append(file.getFileName()+":   "+file.getSize()/1000+"K<br><br>");
        try(ZipFile zipFile=new ZipFile(file.getTargetFile(), Charset.forName("GBK"))){
            for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();){
                ZipEntry entry=e.nextElement();
                if(entry.getName().substring(entry.getName().lastIndexOf("/")+1).length()>0){
                    res.append(entry.getName().substring(entry.getName().lastIndexOf("/")+1)+"<br>");
                }
            }
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(),e);
            return URLEncoder.encode("文件未找到", "UTF-8").replace("+","%20");
        }
        String msg = URLEncoder.encode(res.toString(), "UTF-8").replace("+","%20");
        return msg;
    }


    /**
     * 附件批量上传
     * @param fileList 文件列表
     */
    public AjaxResult FileUploadBatch(List<Map<String, Object>> fileList) throws ZipException, IOException{
        StringBuffer fileCheckMsg = new StringBuffer();
        StringBuffer fileCheckMsg1 = new StringBuffer();
        for(Map<String, Object> map:fileList){
            String srcName=(String) map.get("srcName");
            String fileNo=(String) map.get("fileNo");
            String fileAgentInstitutionCode = "1";
            if(srcName != null && srcName != "") {
                if (srcName.contains("+")) {
                    fileAgentInstitutionCode = srcName.substring(0, srcName.lastIndexOf("+"));
                } else {
                    fileCheckMsg1.append("压缩包必须命名为: 代销机构代码+XX.zip");
                    fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
                }
            }

            String targetFileSTR=(String) map.get("targetFileSTR");
            List<String> agentInstitutionCodeList=(List<String>) map.get("agentInstitutionCodeList");
            File file=new File(targetFileSTR);
            //校验文件名与所选产品代码是否匹配
            Boolean flag=false;
            for(String agentInstitutionCode :agentInstitutionCodeList){
                if(fileAgentInstitutionCode.equals(agentInstitutionCode)){
                    flag=true;
                }
            }
            if(!flag){
                fileCheckMsg1.append("文件名"+srcName+"与所选代销机构代码不匹配！");
                fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
            }
            try
            {
                ZipFile zipFile=new ZipFile(new File(targetFileSTR),Charset.forName("GBK"));

            } catch (Exception e) {
                fileCheckMsg1.append("文件名"+srcName+"中包含不必要的文件或文件夹");
                fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
            }
            if(fileCheckMsg1.length() > 0){
                if(file.exists()){
                    file.delete();
                }
                uploadListener.remove(fileNo);
                fileCheckMsg.append(fileCheckMsg1);
            }
        }
        if(fileCheckMsg.length()>0){
            fileCheckMsg.append("上传失败！");
            return new AjaxResult(fileCheckMsg.toString(), MessageType.错误消息);
        }
        //校验通过，保存文件
        for(Map<String, Object> filemap:fileList){
            String srcName=(String) filemap.get("srcName");
            String fileNo=(String) filemap.get("fileNo");
            String fileProdId=srcName.substring(0, srcName.lastIndexOf("+"));
            String targetFileSTR=(String) filemap.get("targetFileSTR");
            int fileNum=0;
            try (
                    ZipFile zipFile=new ZipFile(new File(targetFileSTR),Charset.forName("GBK"));
            ){
                for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();){
                    ZipEntry entry=e.nextElement();
                    // 统计文件个数
                    if(entry.getName().substring(entry.getName().lastIndexOf("/")+1).length()>0){
                        fileNum++;
                    }
                }
            } catch (Exception e) {
                throw new BusinessException(e);
            }
            AgentInstitutionDto  agentInstitutionDto=new AgentInstitutionDto();
            agentInstitutionDto.setAgentInstitutionCode(fileProdId);
            agentInstitutionDto.setFileNo(fileNo);
            agentInstitutionDto.setEnclosureAddress(targetFileSTR);
            agentInstitutionDto.setEnclosureNum(Integer.toString(fileNum));
            agentInstitutionDto.setEnclosureChangeFlag("");
            // 先查历史数据文件  将 历史文件删除
            String path = "";
            if(StringUtils.isNotEmpty(fileProdId)) {
                path = chbAgentInstitutionInfoMapper.selectFileInfo(agentInstitutionDto);
            }
            chbAgentInstitutionInfoMapper.updateEnclosureNum(agentInstitutionDto);

            // 如果存在历史文件  将文件删除
            if(StringUtils.isNotEmpty(fileProdId) && StringUtil.isNotEmpty(path)){
                File file = new SafelyFile(path);
                if(file.exists()){
                    file.delete();
                }
            }
        }
        return new AjaxResult("附件上传成功！");
    }
}
