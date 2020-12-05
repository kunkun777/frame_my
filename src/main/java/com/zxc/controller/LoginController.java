package com.zxc.controller;

import com.zxc.enums.BaseResponseCode;
import com.zxc.from.vo.DataResult;
import com.zxc.from.vo.req.LoginReqVO;
import com.zxc.from.vo.resp.LoginRespVO;
import com.zxc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(value = "登录模块",description = "用户登录的相关")
public class LoginController {

    @Autowired
    UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public DataResult<LoginRespVO> login( @Valid LoginReqVO vo) throws Exception {
        DataResult result=DataResult.getResult(BaseResponseCode.SUCCESS);
        result.setData(userService.login(vo));
        return result;
    }

}
