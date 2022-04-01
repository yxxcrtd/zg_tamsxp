/*
 * 文件名：EtrExchangeTradeDto002.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chendijun
 * 修改时间：2020年7月2日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.exchange.entrust.etrexchange.agreementrep.dto;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.tams.business.common.dto.PageMode;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.systemtools.util.SysDatadictUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author lby
 * @version 1.0 2020年9月17日
 * @see EtrExchangeUnOpenQuotaRequestDto
 * @since 1.0
 */
@ApiModel(value="非公开行情查询请求参数",description="非公开行情查询请求参数")
public class EtrExchangeUnOpenQuotaRequestDto {

    @ApiModelProperty(name="pageMode",value="分页对象",example="{pageSize: 10,pageNo: 1, sort: 'name', direction: 'asc'}")
    private PageMode pageMode;

    @ApiModelProperty(name="requestId",value="请求编号",example="A1")
    private String requestId;

    @ApiModelProperty(name="bussType",value="业务类型",example="A1")
    private String bussType;

    @ApiModelProperty(name="zyj",value="质押券代码列表",example="A1")
    private List<String> zyj;

    @ApiModelProperty(name="search",value="关键字",example="A1")
    private String search;

    public String getBussType() {
        return bussType;
    }

    public void setBussType(String bussType) {
        this.bussType = bussType;
    }

    public List<String> getZyj() {
        return zyj;
    }

    public void setZyj(List<String> zyj) {
        this.zyj = zyj;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public PageMode getPageMode() {
        return pageMode;
    }

    public void setPageMode(PageMode pageMode) {
        this.pageMode = pageMode;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
