package com.zxc.from.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LoginRespVO {
    @ApiModelProperty("业务访问token")
    private String accessToken;

    @ApiModelProperty("业务token刷新凭证")
    private String refreshToken;

    @ApiModelProperty("用户id")
    private String userId;
}
