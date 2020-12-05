package com.zxc.uitls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitializerUtil {

    public InitializerUtil(TokenSetting tokenSetting){
        log.info("==========>"+tokenSetting.toString());
        JwtTokenUtil.setJwtProperties(tokenSetting);
    }
}
