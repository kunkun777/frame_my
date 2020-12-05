package com.zxc.service.impl;

import com.github.pagehelper.PageHelper;
import com.zxc.contants.Constant;
import com.zxc.dao.ActivityMapper;
import com.zxc.dao.SysUserMapper;
import com.zxc.entity.SysUser;
import com.zxc.enums.BaseResponseCode;
import com.zxc.exception.BusinessException;
import com.zxc.from.vo.req.LoginReqVO;
import com.zxc.from.vo.req.UserPageReqVO;
import com.zxc.from.vo.resp.LoginRespVO;
import com.zxc.from.vo.resp.PageVO;
import com.zxc.service.UserService;
import com.zxc.uitls.JwtTokenUtil;
import com.zxc.uitls.PageUtil;
import com.zxc.uitls.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    SysUserMapper userMapper;
    @Resource
    ActivityMapper activityMapper;

    @Override
    public LoginRespVO login(LoginReqVO vo) throws Exception {
        SysUser user = userMapper.selectByUsername(vo.getUsername());
        if (null == user) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR);
        }

        log.info("==========>com.zxc.controller.LoginController:登录的用户信息："+user.toString());

        if (user.getStatus() == 2) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_LOCK);
        }

        if (!PasswordUtil.matches(user.getSalt(), vo.getPassword(), user.getPassword())) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_PASSWORD_ERROR);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.JWT_USER_NAME, user.getUsername());
        claims.put(Constant.ROLE_INFO_KEY, getRoleByUserId("用户拥有的角色"));
        claims.put(Constant.PERMISSION_INFO_KEY, getPermissionByUserId("用户拥有的权限"));
        String accessToken = JwtTokenUtil.getAccessToken(user.getId(), claims);
        log.info("==>{}", accessToken);
        Map<String, Object> refreshTokenClaims = new HashMap<>();
        refreshTokenClaims.put(Constant.JWT_USER_NAME, user.getUsername());
        String refreshToken;
        if (vo.getType().equals("1")) {
            refreshToken = JwtTokenUtil.getRefreshToken(user.getId(), refreshTokenClaims);
        } else {
            refreshToken = JwtTokenUtil.getRefreshAppToken(user.getId(), refreshTokenClaims);
        }
        log.info("==>{}", refreshToken);
        LoginRespVO re = new LoginRespVO();
        re.setRefreshToken(refreshToken);
        re.setAccessToken(accessToken);
        re.setUserId(user.getId());
        return re;
    }


    //mock数据用的，需要替换成数据库查询
    private List<String> getRoleByUserId(String userId) {
        List<String> list = new ArrayList<>();
        list.add("admin");
        return list;
    }
    private List<String> getPermissionByUserId(String userId) {
        List<String> list = new ArrayList<>();
        list.add("sys:user:add");
        return list;
    }
    @Override
    public PageVO<SysUser> pageInfo(UserPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<SysUser> sysUsers = userMapper.selectAll();
//        PageInfo<SysUser> pageInfo=new PageInfo<>(sysUsers);
        return PageUtil.getPageVO(sysUsers);
    }
}
