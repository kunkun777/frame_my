package com.zxc.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
public class CustomUsernameAndPasswordToken extends UsernamePasswordToken {

    private String jwtToken;

    public CustomUsernameAndPasswordToken(String jwtToken){
        this.jwtToken=jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return this.jwtToken;
    }

    @Override
    public Object getCredentials() {
        return this.jwtToken;
    }
}
