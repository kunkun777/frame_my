package com.zxc.service;

import com.zxc.from.vo.req.LoginReqVO;
import com.zxc.from.vo.req.UserPageReqVO;
import com.zxc.from.vo.resp.LoginRespVO;
import com.zxc.from.vo.resp.PageVO;

public interface UserService {
    LoginRespVO login(LoginReqVO vo) throws Exception;
    PageVO pageInfo(UserPageReqVO vo);
}
