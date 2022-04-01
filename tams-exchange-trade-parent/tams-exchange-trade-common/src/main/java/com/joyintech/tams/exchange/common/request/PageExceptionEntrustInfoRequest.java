package com.joyintech.tams.exchange.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author swwan
 * @date 2021/12/22 - 10:55
 */
@ApiModel(value = "查询分页异常委托信息请求对象", description = "查询分页异常委托信息请求对象")
public class PageExceptionEntrustInfoRequest extends BaseRequest {

    @ApiModelProperty(required = true, name = "pageNum", value = "当前页数，0 表示查出全部", example = "1")
//    @NotNull(message = "当前页数不可为空")
    private Integer pageNum;

    @ApiModelProperty(required = true, name = "pageSize", value = "每页数量，0 表示查出全部", example = "20")
//    @NotNull(message = "每页数量不可为空")
    private Integer pageSize;

    @ApiModelProperty(required = false, name = "entrustStatus", value = "委托状态，以逗号分割", example = "1,3,4")
    @NotNull(message = "委托状态不可为空")
    private Integer entrustStatus;

    @ApiModelProperty(required = true, name = "pageMode", value = "分页请求信息")
    @NotNull(message = "分页请求信息不可为空")
    @Valid
    private PageMode pageMode;

    public PageExceptionEntrustInfoRequest() {
    }


}
