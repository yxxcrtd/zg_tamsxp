package com.joyintech.tams.exchange.common.bean;

/**
 * @ClassName DynamicMonitorDTO
 * @Description 接收动态监控查询参数
 * @Author swwan
 * @Date 2020/11/12 19:39
 * @Version 1.0
 **/
public class DynamicMonitorDTO {
    private String categoryName;

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
