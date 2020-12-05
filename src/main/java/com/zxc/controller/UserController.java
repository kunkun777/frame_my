package com.zxc.controller;

import com.github.pagehelper.PageInfo;
import com.zxc.entity.SysUser;
import com.zxc.enums.BaseResponseCode;
import com.zxc.from.vo.DataResult;
import com.zxc.from.vo.req.UserPageReqVO;
import com.zxc.from.vo.resp.PageVO;
import com.zxc.service.UserService;
import com.zxc.uitls.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(value = "用户模块",description = "用户的相关处理信息")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresPermissions("sys:user:list")
    @ApiOperation("查询全部用户（分页处理）")
    @PostMapping("/selectAll")
    public DataResult<PageVO<SysUser>> pageInfo(UserPageReqVO vo){
        DataResult<PageVO<SysUser>> result=DataResult.getResult(BaseResponseCode.SUCCESS);
        result.setData(userService.pageInfo(vo));
        return result;
    }
}
