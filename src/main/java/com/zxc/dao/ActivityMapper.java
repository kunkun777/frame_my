package com.zxc.dao;

import com.zxc.from.vo.resp.PageVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityMapper {
    public int addActivityByUserId(PageVO activityId, String userId);
}
