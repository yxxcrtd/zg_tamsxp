package com.joyintech.tams.common.svreport.base.service;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.base.dao.BaseCheckOracleMapper;
import com.joyintech.tams.common.svreport.base.dto.BaseCheckOracleDto;
import com.joyintech.tams.common.svreport.base.dto.BaseCommonCheckDto;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Excel导入功能校验
 *
 * @author zhoubin
 */
@Service
public class BaseCheckOracleService {

    private static final String rule = "^-?[0-9]\\d*$";

    @Resource
    private BaseCheckOracleMapper baseCheckOracleMapper;


    /**
     * 获取表中每个字段的相应属性
     *
     * @return
     */
    public Map<String, BaseCheckOracleDto> getTabColumns(String tableName) {
        List<BaseCheckOracleDto> tabColumnsList = baseCheckOracleMapper.selectTabColumns(tableName);
        Map<String, BaseCheckOracleDto> map = tabColumnsList.stream().collect(Collectors.toMap(BaseCheckOracleDto::getColumnName, Function.identity()));
        return map;
    }

    /**
     * 查询表的唯一索引
     *
     * @return
     */
    public List<String> getUniqueConstraint(String tableName) {
        return baseCheckOracleMapper.selectUniqueConstraint(tableName);
    }


    /**
     * 校验列
     */
    public void checkTabColumns(Map<String, BaseCheckOracleDto> tabColumnsMap, int i, List<BaseCommonCheckDto> checkResult, Map<String, String> map) {
        StringBuffer errMsg = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (tabColumnsMap.containsKey(entry.getKey())) {
                BaseCheckOracleDto tabDto = tabColumnsMap.get(entry.getKey());
                String value = entry.getValue();
                //如果字段允许为空，值为空直接返回
                if (Objects.equals("Y", tabDto.getNullAble()) && StringUtil.isBlank(value)) {
                    continue;
                }
                if (StringUtil.isBlank(value)) {
                    errMsg.append("[" + tabDto.getColumnName() + "]：" + "不能为空;");
                    continue;
                }
                //字符型校验
                if (Objects.equals("VARCHAR2", tabDto.getDataType()) || Objects.equals("CHAR", tabDto.getDataType()) || Objects.equals("NVARCHAR2", tabDto.getDataType())) {
                    this.checkString(tabDto, errMsg, value);
                }

                //日期类型校验
                if (Objects.equals("DATE", tabDto.getDataType()) || Objects.equals("TIMESTAMP(6)", tabDto.getDataType())) {
                    this.checkDate(tabDto, errMsg, value);
                }
                //数字类型校验
                if (Objects.equals("NUMBER", tabDto.getDataType())) {
                    this.checkNumber(tabDto, errMsg, value);
                }
            }
        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            map.put("STATUS", "R");
            BaseCommonCheckDto dto = new BaseCommonCheckDto();
            dto.setErrMap(map);
            dto.setExcelValidDto(new ExcelValidDto("0未导入", String.valueOf(i + 2), errMsg.toString()));
            checkResult.add(dto);
        }
    }

    /**
     * oracle检查方法
     *
     * @param dataList
     * @param tableName
     * @return
     */
    public List<BaseCommonCheckDto> check4Oracle(List<Map<String, String>> dataList, String tableName) {

        List<BaseCommonCheckDto> checkResultAll = new LinkedList<>();
        //表中列的属性
        Map<String, BaseCheckOracleDto> tabColumnsMap = this.getTabColumns(tableName);
        //表的唯一约束
        List<String> uniqueList = this.getUniqueConstraint(tableName);
        Set<String> set = new HashSet();
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> map = dataList.get(i);
            //判断是否有重复数据
            if (CollectionUtils.isNotEmpty(uniqueList)) {
                //将唯一索引拼成字符串
                String uniqueKeyValue = this.getUniqueKeyValue(map, uniqueList);
                if (StringUtils.isNotBlank(uniqueKeyValue)) {
                    if (set.contains(uniqueKeyValue)) {
                        //数据重复
                        map.put("STATUS", "R");
                    } else {
                        set.add(uniqueKeyValue);
                    }

                }

            }
            //数据库校验
            this.checkTabColumns(tabColumnsMap, i, checkResultAll, map);


        }

        return checkResultAll;

    }

    /**
     * 将唯一索引拼成字符串
     *
     * @param map
     * @param list
     * @return
     */
    private String getUniqueKeyValue(Map<String, String> map, List<String> list) {
        StringBuffer sb = new StringBuffer("");
        list.forEach(key -> {
            sb.append(StringUtils.isBlank(map.get(key)) ? "" : map.get(key));
        });
        return sb.toString();
    }

    public void checkString(BaseCheckOracleDto tabDto, StringBuffer errMsg, String value) {
        //校验字段长度
        if (tabDto.getLength() != 0 && tabDto.getLength() < value.getBytes().length) {
            errMsg.append("[" + tabDto.getColumnName() + "]：" + "字段长度不能超过" + tabDto.getLength());
        }
    }

    public void checkDate(BaseCheckOracleDto tabDto, StringBuffer errMsg, String value) {
        if (!this.isDate(value)) {
            errMsg.append("[" + tabDto.getColumnName() + "]：" + "不是日期格式;");
            return;
        }
        //校验日期格式
        if (!(DateUtil.isDateStr(value) || DateUtil.isDateStr(value, DateUtil.DEFAULT_ANOTHER_FORMAT))) {
            errMsg.append("[" + tabDto.getColumnName() + "]：" + "日期格式不正确，请使用yyyy-MM-dd或yyyy/MM/dd格式;");
        }

    }

    public void checkNumber(BaseCheckOracleDto tabDto, StringBuffer errMsg, String value) {
        //是否是数字格式
        if (!this.isNumber(value)) {
            errMsg.append("[" + tabDto.getColumnName() + "]：" + "不是数字格式;");
            return;
        }
        // 是否是整数类型
        if (tabDto.getDataScale() == 0) {
            if (!Pattern.matches(rule, value)) {
                errMsg.append("[" + tabDto.getColumnName() + "]：" + "值不是整数;");
                return;
            }
            //整数位是否超限
            if (tabDto.getNumberLength() < value.length()) {
                errMsg.append("此列整数位允许的最大长度为").append(tabDto.getNumberLength()).append("[" + tabDto.getColumnName() + "]：" + "值大于为此列指定的允许精度;");
            }
        }
        //小数位精度判断
        if (tabDto.getDataScale() > 0) {
            //整数位允许长度
            Integer integerLength = tabDto.getNumberLength() - tabDto.getDataScale();
            if (Pattern.matches(rule, value)) {
                if (integerLength < value.length()) {
                    errMsg.append("此列整数位允许的最大长度为").append(integerLength).append("[" + tabDto.getColumnName() + "]：" + "值大于为此列指定的允许精度;");
                }
                return;
            }
            String[] split = value.split("\\.");
            //整数
            String integer = split[0];
            //小数位
            String decimal = split[1];
            //整数位是否超限
            if (integerLength < integer.length()) {
                errMsg.append("此列整数位允许的最大长度为").append(integerLength).append("[" + tabDto.getColumnName() + "]：" + "值大于为此列指定的允许精度;");
            }
            //小数位是否超限
            if (tabDto.getDataScale() < decimal.length()) {
                errMsg.append("此列小数位允许的最大长度为").append(tabDto.getDataScale()).append("[" + tabDto.getColumnName() + "]：" + "值大于为此列指定的允许精度;");
            }


        }
    }

    /**
     * 日期格式校验
     *
     * @param
     * @return 返回boolean
     */
    private boolean isDate(String date) {
        boolean flag = true;
        if (!DateUtil.isDateStr(date, DateUtil.DEFAULT_OTHER_FORMAT, DateUtil.DEFAULT_ANOTHER_FORMAT, DateUtil.DEFAULT_DATE_FORMAT_EN)) {
            flag = false;
        } else {
            date = DateUtil.format(DateUtil.parse(date));
            date = date.replace("-", "").replace("/", "");
            //年
            int year = Integer.valueOf(date.substring(0, 4));
            //月
            int month = Integer.valueOf(date.substring(4, 6));
            //日
            int day = Integer.valueOf(date.substring(6));
            if (month < 1 || month > 12) {
                flag = false;
            } else {
                if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                    if (day < 1 || day > 31) {
                        flag = false;
                    }
                } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                    if (day < 1 || day > 30) {
                        flag = false;
                    }
                } else if (month == 2) {
                    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                        if (day < 1 || day > 29) {
                            flag = false;
                        }
                    } else {
                        if (day < 1 || day > 28) {
                            flag = false;
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 判断是否为数字
     *
     * @param
     * @return 返回boolean
     */
    private boolean isNumber(String figure) {
        try {
            new BigDecimal(figure);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
