package com.zxc.from.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class LoginReqVO {
    @NotBlank(message="用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;
    @NotBlank(message="密码不能为空")
    @ApiModelProperty("密码")
    private String password;
    @NotBlank(message="登录类型不能为空")
    @ApiModelProperty("登录类型（1.web;2.app）")
    private String type;
}
