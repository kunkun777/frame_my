package com.zxc.shiro;


import com.zxc.contants.Constant;
import com.zxc.exception.BusinessException;
import com.zxc.service.impl.RedisService;
import com.zxc.uitls.JwtTokenUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CustomUsernameAndPasswordToken customUsernameAndPasswordToken=(CustomUsernameAndPasswordToken)token;
        String accessToken= (String) customUsernameAndPasswordToken.getCredentials();
        String userId= JwtTokenUtil.getUserId(accessToken);
        if (redisService.hasKey(Constant.DELETED_USER_KEY+userId)){
            throw new BusinessException(400123,"用户已经被删除");
        }
        if (redisService.hasKey(Constant.ACCOUNT_LOCK_KEY+userId)){
            throw new BusinessException(400123,"用户已经被锁定");
        }
        if (!JwtTokenUtil.validateToken(accessToken)){
            throw new BusinessException(400123,"凭证过期,请刷新客户端");
        }
            return true;
    }
}
