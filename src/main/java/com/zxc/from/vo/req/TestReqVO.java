package com.zxc.from.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TestReqVO {
    @NotEmpty(message = "数据不能为空")
    @ApiModelProperty(value = "list集合数据")
    private List<String> list;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotNull(message = "年龄不能为空")
    @ApiModelProperty(value = "年龄")
    private Integer age;
}
