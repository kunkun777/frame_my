package com.zxc.controller;

import com.zxc.entity.SysUser;
import com.zxc.enums.BaseResponseCode;
import com.zxc.from.vo.DataResult;
import com.zxc.from.vo.req.TestReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "测试接口",description = "为了测试接口用")
@RequestMapping("/swagger")
public class TestController {

    @GetMapping("/test")
    @ApiOperation("专用于测试连接时使用")
    public String  testSwagger(){
        return "测试联通成功";
    }

    @GetMapping
    @ApiOperation("统一的响应格式测试方法")
    public DataResult test(){
        SysUser user=new SysUser();
        List<SysUser> users=new ArrayList<>();
        user.setUsername("张三");
        user.setPhone("1231213123");
        SysUser user1=new SysUser();
        user1.setUsername("张三");
        user1.setPhone("1231213123");
        users.add(user);
        users.add(user1);
        return DataResult.getResult(BaseResponseCode.SUCCESS,users);
    }

//    @GetMapping("/t/${type}")
//    @ApiOperation("统一的响应格式测试方法")
//    public DataResult testBusinessExcept
//    ion(@PathVariable(value = "type") String type){
//        if(type.equals("1")){
//            throw new BusinessException(BaseResponseCode.BAD_REQUEST);
//        }
//        return DataResult.getResult(200,type);
//    }
    @PostMapping("/test/valid")
    @ApiOperation("测试检验验证器")
    public DataResult testValid(@RequestBody @Valid TestReqVO reqVO){
        return DataResult.getResult(BaseResponseCode.VALIDATOR_ERROR);
    }
}
