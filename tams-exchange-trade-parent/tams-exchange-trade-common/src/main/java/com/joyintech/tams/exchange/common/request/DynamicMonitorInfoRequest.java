package com.joyintech.tams.exchange.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "查询动态监控信息请求对象", description = "查询动态监控信息请求对象")
public class DynamicMonitorInfoRequest {

    @ApiModelProperty(required = false, name = "categoryName", value = "监控类目，为空时查询所有监控类目", example = "在线账户")
    private String categoryName;

    @ApiModelProperty(required = false, name = "snapshotTime", value = "时间戳，为空时查询截止当前时间点所有数据", example = "1644390517000")
    private Long snapshotTime;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getSnapshotTime() {
        return snapshotTime;
    }

    public void setSnapshotTime(Long snapshotTime) {
        this.snapshotTime = snapshotTime;
    }

    @Override
    public String toString() {
        return "DynamicMonitorDTO{" +
                "categoryName='" + categoryName + '\'' +
                ", snapshotTime=" + snapshotTime +
                '}';
    }
}
