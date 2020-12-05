package com.zxc.uitls;

import com.zxc.enums.BaseResponseCode;
import com.zxc.exception.BusinessException;
import com.zxc.from.vo.resp.PageVO;
import lombok.NoArgsConstructor;
import com.github.pagehelper.Page;

import java.util.List;

@NoArgsConstructor
public class PageUtil {
    public static <T> PageVO getPageVO(List<T> list){
        PageVO<T> pageVO=new PageVO<>();
        if (list instanceof Page){
            Page page = (Page) list;
            pageVO.setTotalRows(page.getTotal());
            pageVO.setData(page.getResult());
            pageVO.setPageNum(page.getPageNum());
            pageVO.setCurPageSize(page.size());
            pageVO.setTotalPages(page.getPageNum());
        }else {
            throw new BusinessException(BaseResponseCode.BAD_REQUEST);
        }
        return pageVO;
    }
}
