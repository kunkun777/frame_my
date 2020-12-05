package com.zxc.uitls;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Data
public class MyStringRedisSerializer implements RedisSerializer<Object> {
    private final Charset charset;

    public MyStringRedisSerializer(){
        this(StandardCharsets.UTF_8);
    }

    public MyStringRedisSerializer(Charset charset) {
        Assert.notNull(charset,"charset must be not null");
        this.charset = charset;
    }

    @Override
    public byte[] serialize(Object o) {
        if (o==null){
            return new byte[0];
        }
        if (o instanceof String){
            return o.toString().getBytes();
        }else {
            String string= JSON.toJSONString(o);
            return string.getBytes();
        }
    }

    @Override
    public Object deserialize(byte[] bytes) {
        return (bytes == null ?null:new String(bytes,charset));
    }
}
