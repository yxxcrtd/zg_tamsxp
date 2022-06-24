/*
 * 文件名：TableStructService.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：何阳阳
 * 修改时间：2017年12月27日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.report.db.struct.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.db.struct.dao.SvrTableStructMapper;
import com.joyintech.tams.report.db.struct.dto.TableColumn;


/**
 * 数据库表结构查询Service
 * @author 何阳阳
 * @version 1.0 2017年12月27日
 * @see SvrTableStructService
 * @since 1.0
 */
@Service
public class SvrTableStructService {

    /**
     * TableStructMapper
     */
    @Autowired
    private SvrTableStructMapper mapper;

    /**
     * 查询表结构
     * @param tableName 表名
     * @return 表字段列表
     */
    public List<TableColumn> selectTableStruct(String tableName) {

        if(StringUtil.isNotEmpty(tableName)) {
            String databaseType = BaseCommonCheckService.isMYSQLDataBase()?"MYSQL":"Oracle";
            Map<String, Object> param = new HashMap<>();
            param.put("databaseType", databaseType);
            param.put("table", tableName);

            return mapper.selectTableStruct(param);
        }
        return null;
    }
}
