package com.zxc.from.vo.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserPageReqVO {

    @ApiModelProperty(value = "页码")
    private Integer pageNum;
    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;

}
