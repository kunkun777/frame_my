package com.zxc.shiro;

import com.zxc.contants.Constant;
import com.zxc.uitls.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;

@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomUsernameAndPasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CustomUsernameAndPasswordToken customUsernameAndPasswordToken=(CustomUsernameAndPasswordToken)token;
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(customUsernameAndPasswordToken.getPrincipal(),customUsernameAndPasswordToken.getCredentials(),getName());
        log.info("===>用户认证域");
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String accessToken=(String) principals.getPrimaryPrincipal();
        Claims claims= JwtTokenUtil.getClaimsFromToken(accessToken);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        if (null!=claims.get(Constant.PERMISSION_INFO_KEY)){
            info.addStringPermissions((Collection<String>) claims.get(Constant.PERMISSION_INFO_KEY));
        }
        if (null!=claims.get(Constant.ROLE_INFO_KEY)){
            info.addRoles((Collection<String>) claims.get(Constant.ROLE_INFO_KEY));
        }
        return info;
    }
}
