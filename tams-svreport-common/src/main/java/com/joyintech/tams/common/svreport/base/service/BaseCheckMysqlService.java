package com.joyintech.tams.common.svreport.base.service;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.base.dao.BaseCheckMysqlMapper;
import com.joyintech.tams.common.svreport.base.dto.BaseCheckMysqlDto;
import com.joyintech.tams.common.svreport.base.dto.BaseCommonCheckDto;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wubingbing
 * @date 2022-03-31 14:52
 */
@Component
public class BaseCheckMysqlService {
    private String rule = "^-?[1-9]\\d*$";
    private String floatRule = "^(\\-|\\+)?\\d+(\\.\\d+)?$";
    @Autowired
    private BaseCheckMysqlMapper baseCheckMysqlMapper;
    @Value("${spring.datasource.url}")
    private String url;

    public List<BaseCheckMysqlDto> getColumns(String schema,String table){
        return baseCheckMysqlMapper.select(schema,table);
    }

    public List<BaseCommonCheckDto> check4Mysql(List<Map<String,String>> list, String tableName) {
        String[] schema=url.split("/");

        List<BaseCheckMysqlDto> columns = getColumns(schema[3].substring(0,schema[3].indexOf("?")),tableName);
        List<BaseCommonCheckDto> result = new ArrayList<>();
        List<Map<String,String>> errList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            boolean errFlag = false;//检查结果 true为有错误
            Set<Map.Entry<String, String>> set = list.get(i).entrySet();
            for (Map.Entry<String, String> entry : set) {
                for (BaseCheckMysqlDto c : columns) {
                    if (null != entry.getKey() && entry.getKey().equals(c.getColumnName())) {
                        // 非空校验
                        if (c.getIsNullable().equals("NO")) {
                            if (!StringUtils.hasText(entry.getValue())) {
                                ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "为空");
                                BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                commonCheckDto.setErrMap(list.get(i));
                                commonCheckDto.setExcelValidDto(dto);
                                result.add(commonCheckDto);
                                errList.add(list.get(i));
                                // 跳出 for (Column c : columns)循环
                                errFlag = true;
                                break;
                            }
                        }else {
                            //值为空不校验
                            if(org.apache.commons.lang3.StringUtils.isBlank(entry.getValue())){
                                break;
                            }
                        }
                        //表字段是字符类型
                        if (c.getDataType().equals("varchar")||
                            c.getDataType().equals("tinyblob")||
                            c.getDataType().equals("tinytext")||
                            c.getDataType().equals("longblob")||
                            c.getDataType().equals("longtext")) {
                            // 长度校验
                            if (entry.getValue().length() > Integer.valueOf(c.getCharacterMaximumLength())) {
                                ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "内容过长");
                                BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                commonCheckDto.setErrMap(list.get(i));
                                commonCheckDto.setExcelValidDto(dto);
                                result.add(commonCheckDto);
                                errList.add(list.get(i));
                                errFlag = true;
                            }
                            break;
                        } else if (c.getDataType().equals("int")||c.getDataType().equals("bigint")) {
                            // 长度校验
                            if (entry.getValue().length() > Integer.valueOf(c.getNumericPrecision())) {
                                ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "内容过长");
                                BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                commonCheckDto.setErrMap(list.get(i));
                                commonCheckDto.setExcelValidDto(dto);
                                result.add(commonCheckDto);
                                errList.add(list.get(i));
                                errFlag = true;
                                break;
                            }else if(!Pattern.matches(rule,entry.getValue())){
                                //整数校验
                                ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "不是整数");
                                BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                commonCheckDto.setErrMap(list.get(i));
                                commonCheckDto.setExcelValidDto(dto);
                                result.add(commonCheckDto);
                                errList.add(list.get(i));
                                errFlag = true;
                                break;
                            }
                            break;
                        }else if(c.getDataType().equals("float")||c.getDataType().equals("double")||c.getDataType().equals("decimal")){
                            if(!Pattern.matches(floatRule,entry.getValue())){
                                ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "不是整数或小数");
                                BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                commonCheckDto.setErrMap(list.get(i));
                                commonCheckDto.setExcelValidDto(dto);
                                result.add(commonCheckDto);
                                errList.add(list.get(i));
                                errFlag = true;
                                break;
                            }
                            if(!entry.getValue().contains(".")){
                                //负数，负号不计算
                                if(entry.getValue().contains("-")){
                                    if (entry.getValue().length()-1 > (Integer.valueOf(c.getNumericPrecision())-Integer.valueOf(c.getNumericScale()))) {
                                        ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "内容过长");
                                        BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                        commonCheckDto.setErrMap(list.get(i));
                                        commonCheckDto.setExcelValidDto(dto);
                                        result.add(commonCheckDto);
                                        errList.add(list.get(i));
                                        errFlag = true;
                                        break;
                                    }
                                }
                                if(!entry.getValue().contains("-")){
                                    if (entry.getValue().length() > (Integer.valueOf(c.getNumericPrecision())-Integer.valueOf(c.getNumericScale()))) {
                                        ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "内容过长");
                                        BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                        commonCheckDto.setErrMap(list.get(i));
                                        commonCheckDto.setExcelValidDto(dto);
                                        result.add(commonCheckDto);
                                        errList.add(list.get(i));
                                        errFlag = true;
                                        break;
                                    }
                                }
                            }
                            if(entry.getValue().contains(".")){
                                //负数，负号不计算
                                if(entry.getValue().contains("-")){
                                    if (entry.getValue().split("\\.")[0].length()-1 > (Integer.valueOf(c.getNumericPrecision())-Integer.valueOf(c.getNumericScale()))) {
                                        ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "内容过长");
                                        BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                        commonCheckDto.setErrMap(list.get(i));
                                        commonCheckDto.setExcelValidDto(dto);
                                        result.add(commonCheckDto);
                                        errList.add(list.get(i));
                                        errFlag = true;
                                        break;
                                    }
                                }
                                if(!entry.getValue().contains("-")){
                                    if (entry.getValue().split("\\.")[0].length() > (Integer.valueOf(c.getNumericPrecision())-Integer.valueOf(c.getNumericScale()))) {
                                        ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "内容过长");
                                        BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                        commonCheckDto.setErrMap(list.get(i));
                                        commonCheckDto.setExcelValidDto(dto);
                                        result.add(commonCheckDto);
                                        errList.add(list.get(i));
                                        errFlag = true;
                                        break;
                                    }
                                }
                                if (entry.getValue().split("\\.")[1].length() > Integer.valueOf(c.getNumericScale())) {
                                    ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "小数位过长");
                                    BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                    commonCheckDto.setErrMap(list.get(i));
                                    commonCheckDto.setExcelValidDto(dto);
                                    result.add(commonCheckDto);
                                    errList.add(list.get(i));
                                    errFlag = true;
                                    break;
                                }
                            }
                                break;
                        }else if(c.getDataType().equals("date")){
                            if(!(DateUtil.isDateStr(entry.getValue())||DateUtil.isDateStr(entry.getValue(),DateUtil.DEFAULT_ANOTHER_FORMAT))){
                                ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "日期格式不正确，请使用yyyy-MM-dd或yyyy/MM/dd格式");
                                BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                commonCheckDto.setErrMap(list.get(i));
                                commonCheckDto.setExcelValidDto(dto);
                                result.add(commonCheckDto);
                                errList.add(list.get(i));
                                errFlag = true;
                            }
                            break;
                        }else if(c.getDataType().equals("datetime")||c.getDataType().equals("timestamp")){
                            if(!DateUtil.isDateStr(entry.getValue(),DateUtil.DEFAULT_DATETIME_FORMAT)){
                                ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), c.getColumnComment() + "日期格式不正确，请使用yyyy-MM-dd HH:mm:ss格式");
                                BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                commonCheckDto.setErrMap(list.get(i));
                                commonCheckDto.setExcelValidDto(dto);
                                result.add(commonCheckDto);
                                errList.add(list.get(i));
                                errFlag = true;
                            }
                            break;
                        }

                        break;
                    }
                }
            }
            if(errFlag){
                list.get(i).put("STATUS","R");
            }
        }

        List<Map<String,String>> uniList = new ArrayList<>();
        List<Map<String,String>> priList = new ArrayList<>();
        if(list.size()>1) {
            Map<String, String> map = null;
            //唯一索引校验
            for (BaseCheckMysqlDto c : columns) {
                if (c.getColumnKey().equals("UNI")) {
                    for (int i = 0; i < list.size(); i++) {
                        map = list.get(i);
                        for (int j = i + 1; j < list.size(); j++) {
                            if (list.get(j).get(c.getColumnName()).equals(map.get(c.getColumnName()))) {
                                ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), "表中数据重复");
                                BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                                map.put("STATUS", "R");
                                commonCheckDto.setErrMap(map);
                                commonCheckDto.setExcelValidDto(dto);
                                result.add(commonCheckDto);
                                uniList.add(list.get(j));
                                break;
                            }
                        }
                    }

                }
            }

            //主键组合索引列
            List<String> primaries = new ArrayList<>();
            for (BaseCheckMysqlDto c : columns) {
                if (c.getColumnKey().equals("PRI")) {
                    primaries.add(c.getColumnName());
                }
            }
            Map<String, String> var1 = null;
            Map<String, String> var2 = null;

            if (primaries.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    var1 = list.get(i);
                    for (int j = i + 1; j < list.size(); j++) {
                        var2 = list.get(j);
                        int num = 0;
                        for (String s : primaries) {
                            if (!Objects.equals(var1.get(s),var2.get(s))) {
                                break;
                            }
                            num++;
                        }
                        if (num == primaries.size()) {
                            ExcelValidDto dto = new ExcelValidDto("0未导入", String.valueOf(i + 2), "表中数据重复");
                            BaseCommonCheckDto commonCheckDto = new BaseCommonCheckDto();
                            var1.put("STATUS", "R");
                            commonCheckDto.setErrMap(list.get(j));
                            commonCheckDto.setExcelValidDto(dto);
                            result.add(commonCheckDto);
                            priList.add(list.get(j));
                            break;
                        }
                    }
                }
            }
        }
//        // 移除唯一索引重复的数据
//        list.removeAll(uniList);
//        // 移除组合主键索引重复的数据
//        list.removeAll(priList);
        return result;
    }


}
