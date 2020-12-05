package com.zxc.dao;

import com.github.pagehelper.PageInfo;
import com.zxc.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    SysUser selectByUsername(String username);
    List<SysUser> selectAll();
}