package com.zxc;

import com.zxc.dao.SysUserMapper;
import com.zxc.service.impl.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.index.PathBasedRedisIndexDefinition;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;


@SpringBootTest
@RunWith(SpringRunner.class)
public class testApplication {

    @Autowired
    private RedisService redisService;
    @Resource
    private SysUserMapper userMapper;
    @Test
    public void contestLoad(){
//        System.out.println("====="+userMapper.selectByPrimaryKey("1").toString());
//        System.out.println(userMapper.selectByUsername("zxc").toString());
//        System.out.println(userMapper.selectByPrimaryKey("2").toString());
        System.out.println(Arrays.toString(DatatypeConverter.parseBase64Binary("sdfasdfasdfasd")));
//        redisService.set("1","1");
    }
}
